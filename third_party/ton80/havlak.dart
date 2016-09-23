// Copyright 2012 Google Inc.
// All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// https://github.com/dart-lang/ton80/blob/master/lib/src/common/dart/BenchmarkBase.dart
// Copyright 2011 Google Inc. All Rights Reserved.
class Expect {
  static void equals(var expected, var actual) {
    if (expected != actual) {
      print("Values not equal: ");
      print(expected);
      print("vs ");
      print(actual);
    }
  }

  static void listEquals(List expected, List actual) {
    if (expected.length != actual.length) {
      print("Lists have different lengths: ");
      print(expected.length);
      print("vs ");
      print(actual.length);
    }
    for (int i = 0; i < actual.length; i++) {
      equals(expected[i], actual[i]);
    }
  }

  void fail(String message) {
    print(message);
  }
}


class BenchmarkBase {
  final String name;

  // Empty constructor.
  const BenchmarkBase(String name) : this.name = name;

  static const int iters = 1000;

  // The benchmark code.
  // This function is not used, if both [warmup] and [exercise] are overwritten.
  void run() { }

  // Runs a short version of the benchmark. By default invokes [run] once.
  void warmup() {
    run();
  }

  // Exercices the benchmark. By default invokes [run] 10 times.
  void exercise() {
    for (int i = 0; i < iters; i++) {
      run();
    }
  }

  // Not measured setup code executed prior to the benchmark runs.
  void setup() { }

  // Not measures teardown code executed after the benchark runs.
  void teardown() { }

  // Measures the score for this benchmark by executing it repeately until
  // time minimum has been reached.
  double measureForWarumup(int timeMinimum) {
    int time = 0;
    int iter = 0;
    Stopwatch watch = new Stopwatch();
    watch.start();
    int elapsed = 0;
    while (elapsed < timeMinimum) {
      warmup();
      elapsed = watch.elapsedMilliseconds;
      iter++;
    }
    return (1000.0 * elapsed / iter) / iters;
  }

  // Measures the score for this benchmark by executing it repeately until
  // time minimum has been reached.
  double measureForExercise(int timeMinimum) {
    int time = 0;
    int iter = 0;
    Stopwatch watch = new Stopwatch();
    watch.start();
    int elapsed = 0;
    while (elapsed < timeMinimum) {
      exercise();
      elapsed = watch.elapsedMilliseconds;
      iter++;
    }
    return (1000.0 * elapsed / iter) / iters;
  }

  // Measures the score for the benchmark and returns it.
  double measure() {
    setup();
    // Warmup for at least 100ms. Discard result.
    measureForWarumup(100);
    // Run the benchmark for at least 2000ms.
    double result = measureForExercise(2 * 1000);
    teardown();
    return result;
  }

  void report() {
    double score = measure();
    print("$name(RunTime): $score us.");
  }

}

// import "dart:collection";

main() {
  new Havlak().report();
}

int mix(int existing, int value) {
  return ((existing & 0x0fffffff) << 1) + value;
}

//======================================================
// Scaffold Code
//======================================================

// BasicBlock's static members
//
int numBasicBlocks = 0;
int getNumBasicBlocks() => numBasicBlocks;

//
// class BasicBlock
//
// BasicBlock only maintains a vector of in-edges and
// a vector of out-edges.
//
class BasicBlock {
  final int name;
  List<BasicBlock> inEdges  = [];
  List<BasicBlock> outEdges = [];

  BasicBlock(this.name) {
    numBasicBlocks++;
  }

  String toString() => "BB$name";
  int getNumPred() => inEdges.length;
  int getNumSucc() => outEdges.length;
  bool addInEdge(BasicBlock bb) => inEdges.add(bb);
  bool addOutEdge(BasicBlock bb) => outEdges.add(bb);
}

//
// class BasicBlockEdge
//
// These data structures are stubbed out to make the code below easier
// to review.
//
// BasicBlockEdge only maintains two pointers to BasicBlocks.
//
class BasicBlockEdge {
  BasicBlock from, to;

  BasicBlockEdge(CFG cfg, int fromName, int toName) {
    from = cfg.createNode(fromName);
    to = cfg.createNode(toName);

    from.addOutEdge(to);
    to.addInEdge(from);

    cfg.addEdge(this);
  }
}


//
// class CFG
//
// CFG maintains a list of nodes, plus a start node.
// That's it.
//
class CFG {
  final Map<int, BasicBlock> basicBlockMap = new Map();
  final List<BasicBlockEdge> edgeList = [];
  BasicBlock startNode;

  BasicBlock createNode(int name) {
    // (springerm): Had to change this due to non-nullability
    BasicBlock node; // = basicBlockMap[name];
    if (!basicBlockMap.containsKey(name)) {
    // if (node == null) {
      node = new BasicBlock(name);
      basicBlockMap[name] = node;
    } else {
      node = basicBlockMap[name];
    }

    if (getNumNodes() == 1) {
      startNode = node;
    }
    return node;
  }

  bool addEdge(BasicBlockEdge edge) => edgeList.add(edge);
  int getNumNodes() => basicBlockMap.length;
  BasicBlock getDst(BasicBlockEdge edge) => edge.to;
  BasicBlock getSrc(BasicBlockEdge edge) => edge.from;
}

//
// class SimpleLoop
//
// Basic representation of loops, a loop has an entry point,
// one or more exit edges, a set of basic blocks, and potentially
// an outer loop - a "parent" loop.
//
// Furthermore, it can have any set of properties, e.g.,
// it can be an irreducible loop, have control flow, be
// a candidate for transformations, and what not.
//
class SimpleLoop  {
  final List<BasicBlock> basicBlocks = [];
  final List<SimpleLoop> children = [];
  final int counter;

  SimpleLoop parent;
  BasicBlock header;

  bool isRoot = false;
  bool isReducible = true;
  int nestingLevel = 0;
  int depthLevel = 0;

  SimpleLoop(this.counter);

  bool addNode(BasicBlock bb) => basicBlocks.add(bb);
  bool addChildLoop(SimpleLoop loop) => children.add(loop);

  void setParent_(SimpleLoop p) {
    this.parent = p;
    p.addChildLoop(this);
  }

  void setHeader_(BasicBlock bb) {
    basicBlocks.add(bb);
    header = bb;
  }

  void setNestingLevel_(int level) {
    nestingLevel = level;
    if (level == 0) {
      isRoot = true;
    }
  }

  int checksum() {
    int result = counter;
    result = mix(result, isRoot ? 1 : 0);
    result = mix(result, isReducible ? 1 : 0);
    result = mix(result, nestingLevel);
    result = mix(result, depthLevel);
    if (header != null) result = mix(result, header.name);

    for(var e in basicBlocks) {
      result = mix(result, e.name);
    }
    for (var e in children) {
      result = mix(result, e.checksum());
    }
    return result;
  }
}


//
// LoopStructureGraph
//
// Maintain loop structure for a given CFG.
//
// Two values are maintained for this loop graph, depth, and nesting level.
// For example:
//
// loop        nesting level    depth
//----------------------------------------
// loop-0      2                0
//   loop-1    1                1
//   loop-3    1                1
//     loop-2  0                2
//
class LSG {
  int loopCounter = 1;
  final List<SimpleLoop> loops = [];
  final SimpleLoop root = new SimpleLoop(0);

  LSG() {
    root.setNestingLevel_(0);
    loops.add(root);
  }

  SimpleLoop createNewLoop() {
    SimpleLoop loop = new SimpleLoop(loopCounter++);
    return loop;
  }

  bool addLoop(SimpleLoop loop) => loops.add(loop);

  int checksum() {
    int result = loops.length;
    var ebefore = null;

    for (var e in loops) {
      result = mix(result, e.checksum());
    }
    return mix(result, root.checksum());
  }

  int getNumLoops() => loops.length;
}


//======================================================
// Main Algorithm
//======================================================

//
// class UnionFindNode
//
// The algorithm uses the Union/Find algorithm to collapse
// complete loops into a single node. These nodes and the
// corresponding functionality are implemented with this class
//
class UnionFindNode {
  int dfsNumber = 0;
  UnionFindNode parent;
  BasicBlock bb;
  SimpleLoop loop;

  UnionFindNode();

  // Initialize this node.
  //
  void initNode(BasicBlock bb, int dfsNumber) {
    parent = this;
    this.bb = bb;
    this.dfsNumber = dfsNumber;
  }

  // Union/Find Algorithm - The find routine.
  //
  // Implemented with Path Compression (inner loops are only
  // visited and collapsed once, however, deep nests would still
  // result in significant traversals).
  //
  UnionFindNode findSet() {
    List<UnionFindNode> nodeList = [];

    UnionFindNode node = this;
    while (node != node.parent) {
      if (node.parent != node.parent.parent)
        nodeList.add(node);

      node = node.parent;
    }

    // Path Compression, all nodes' parents point to the 1st level parent.
    for (int iter=0; iter < nodeList.length; ++iter) {
      nodeList[iter].parent = node.parent;
    }

    return node;
  }

  // Union/Find Algorithm - The union routine.
  //
  // Trivial. Assigning parent pointer is enough,
  // we rely on path compression.
  //
  void union(UnionFindNode unionFindNode) {
    parent = unionFindNode;
  }
  SimpleLoop setLoop_(SimpleLoop l) => loop = l;
}



class HavlakLoopFinder {
  final CFG cfg;
  final LSG lsg;

  static const int BB_TOP          = 0; // uninitialized
  static const int BB_NONHEADER    = 1; // a regular BB
  static const int BB_REDUCIBLE    = 2; // reducible loop
  static const int BB_SELF         = 3; // single BB loop
  static const int BB_IRREDUCIBLE  = 4; // irreducible loop
  static const int BB_DEAD         = 5; // a dead BB
  static const int BB_LAST         = 6; // Sentinel

  // Marker for uninitialized nodes.
  static const int UNVISITED = -1;

  // Safeguard against pathologic algorithm behavior.
  static const int MAXNONBACKPREDS = (32 * 1024);

  HavlakLoopFinder(this.cfg, this.lsg);

  //
  // IsAncestor
  //
  // As described in the paper, determine whether a node 'w' is a
  // "true" ancestor for node 'v'.
  //
  // Dominance can be tested quickly using a pre-order trick
  // for depth-first spanning trees. This is why DFS is the first
  // thing we run below.
  //
  bool isAncestor(int w, int v, List<int> last) {
    return (w <= v) && (v <= last[w]);
  }

  //
  // DFS - Depth-First-Search
  //
  // DESCRIPTION:
  // Simple depth first traversal along out edges with node numbering.
  //
  int DFS(BasicBlock currentNode,
          List<UnionFindNode> nodes,
          List<int> number,
          List<int> last, int current) {
    nodes[current].initNode(currentNode, current);
    number[currentNode.name] = current;

    int lastid = current;
    for (int target = 0; target < currentNode.outEdges.length; target++) {
      if (number[currentNode.outEdges[target].name] == UNVISITED)
        lastid = DFS(currentNode.outEdges[target], nodes, number,
                     last, lastid + 1);
    }

    last[number[currentNode.name]] = lastid;
    return lastid;
  }

  //
  // findLoops
  //
  // Find loops and build loop forest using Havlak's algorithm, which
  // is derived from Tarjan. Variable names and step numbering has
  // been chosen to be identical to the nomenclature in Havlak's
  // paper (which, in turn, is similar to the one used by Tarjan).
  //
  int findLoops() {
    if (cfg.startNode == null) {
      return 0;
    }

    int size = cfg.getNumNodes();

    List<List<int>> nonBackPreds = new List(size);
    List<List<int>> backPreds = new List(size);
    List<int> number = new List(size);
    List<int> header = new List(size);
    List<int> types = new List(size);
    List<int> last = new List(size);
    List<UnionFindNode> nodes = new List(size);

    for (int i = 0; i < size; ++i) {
      nonBackPreds[i] = [];
      backPreds[i] = [];
      number[i] = UNVISITED;
      header[i] = 0;
      types[i] = BB_NONHEADER;
      last[i] = 0;
      nodes[i] = new UnionFindNode();
    }

    // Step a:
    //   - initialize all nodes as unvisited.
    //   - depth-first traversal and numbering.
    //   - unreached BB's are marked as dead.
    //
    DFS(cfg.startNode, nodes, number, last, 0);

    // Step b:
    //   - iterate over all nodes.
    //
    //   A backedge comes from a descendant in the DFS tree, and non-backedges
    //   from non-descendants (following Tarjan).
    //
    //   - check incoming edges 'v' and add them to either
    //     - the list of backedges (backPreds) or
    //     - the list of non-backedges (nonBackPreds)
    //
    for (int w = 0; w < size; ++w) {
      BasicBlock nodeW = nodes[w].bb;
      if (nodeW == null) {
        types[w] = BB_DEAD;
      } else {
        if (nodeW.getNumPred() > 0) {
          for (int nv = 0; nv < nodeW.inEdges.length; ++nv) {
            BasicBlock nodeV = nodeW.inEdges[nv];
            int v = number[nodeV.name];
            if (v != UNVISITED) {
              if (isAncestor(w, v, last)) {
                backPreds[w].add(v);
              } else {
                nonBackPreds[w].add(v);
              }
            }
          }
        }
      }
    }

    // Step c:
    //
    // The outer loop, unchanged from Tarjan. It does nothing except
    // for those nodes which are the destinations of backedges.
    // For a header node w, we chase backward from the sources of the
    // backedges adding nodes to the set P, representing the body of
    // the loop headed by w.
    //
    // By running through the nodes in reverse of the DFST preorder,
    // we ensure that inner loop headers will be processed before the
    // headers for surrounding loops.
    //
    for (int w = size-1; w >=0; --w) {
      // this is 'P' in Havlak's paper
      List<UnionFindNode> nodePool = [];

      BasicBlock nodeW = nodes[w].bb;
      if (nodeW == null) {
        continue;
      }

      // Step d:
      for (int vi = 0; vi < backPreds[w].length; ++vi) {
        var v = backPreds[w][vi];
        if (v != w) {
          nodePool.add(nodes[v].findSet());
        } else {
          types[w] = BB_SELF;
        }
      }

      // Copy nodePool to workList.
      //
      List<UnionFindNode> workList = [];
      for (int n = 0; n < nodePool.length; ++n) {
        workList.add(nodePool[n]);
      }

      if (nodePool.length != 0) {
        types[w] = BB_REDUCIBLE;
      }
      // work the list...
      //
      while (workList.length > 0) {
        UnionFindNode x = workList.removeAt(0);

        // Step e:
        //
        // Step e represents the main difference from Tarjan's method.
        // Chasing upwards from the sources of a node w's backedges. If
        // there is a node y' that is not a descendant of w, w is marked
        // the header of an irreducible loop, there is another entry
        // into this loop that avoids w.
        //

        // The algorithm has degenerated. Break and
        // return in this case.
        //
        int nonBackSize = nonBackPreds[x.dfsNumber].length;
        if (nonBackSize > MAXNONBACKPREDS) {
          return 0;
        }

        for (int iter=0; iter < nonBackPreds[x.dfsNumber].length; ++iter) {
          UnionFindNode y = nodes[nonBackPreds[x.dfsNumber][iter]];
          UnionFindNode ydash = y.findSet();

          if (!isAncestor(w, ydash.dfsNumber, last)) {
            types[w] = BB_IRREDUCIBLE;
            nonBackPreds[w].add(ydash.dfsNumber);
          } else {
            if (ydash.dfsNumber != w) {
              if (nodePool.indexOf(ydash) == -1) {
                workList.add(ydash);
                nodePool.add(ydash);
              }
            }
          }
        }
      }

      // Collapse/Unionize nodes in a SCC to a single node
      // For every SCC found, create a loop descriptor and link it in.
      //
      if ((nodePool.length > 0) || (types[w] == BB_SELF)) {
        SimpleLoop loop = lsg.createNewLoop();

        loop.setHeader_(nodeW);
        if (types[w] == BB_IRREDUCIBLE) {
          loop.isReducible = true;
        } else {
          loop.isReducible = false;
        }

        // At this point, one can set attributes to the loop, such as:
        //
        // the bottom node:
        //    iter  = backPreds(w).begin();
        //    loop bottom is: nodes(iter).node;
        //
        // the number of backedges:
        //    backPreds(w).size()
        //
        // whether this loop is reducible:
        //    types(w) != BB_IRREDUCIBLE
        //
        nodes[w].loop = loop;

        for (int np = 0; np < nodePool.length; ++np) {
          UnionFindNode node = nodePool[np];

          // Add nodes to loop descriptor.
          header[node.dfsNumber] = w;
          node.union(nodes[w]);

          // Nested loops are not added, but linked together.
          if (node.loop != null) {
            node.loop.setParent_(loop);
          } else {
            loop.addNode(node.bb);
          }
        }
        lsg.addLoop(loop);
      } // nodePool.length
    } // Step c

    return lsg.getNumLoops();
  } // findLoops
} // HavlakLoopFinder


//======================================================
// Testing Code
//======================================================

int buildDiamond(CFG cfg, int start) {
  var bb0 = start;
  new BasicBlockEdge(cfg, bb0, bb0 + 1);
  new BasicBlockEdge(cfg, bb0, bb0 + 2);
  new BasicBlockEdge(cfg, bb0 + 1, bb0 + 3);
  new BasicBlockEdge(cfg, bb0 + 2, bb0 + 3);
  return bb0 + 3;
}


void buildConnect(CFG cfg, int start, int end) {
  new BasicBlockEdge(cfg, start, end);
}

int buildStraight(CFG cfg, int start, int n) {
  for (int i=0; i < n; i++) {
    buildConnect(cfg, start + i, start + i + 1);
  }
  return start + n;
}

int buildBaseLoop(CFG cfg, int from) {
  int header   = buildStraight(cfg, from, 1);
  int diamond1 = buildDiamond(cfg, header);
  int d11      = buildStraight(cfg, diamond1, 1);
  int diamond2 = buildDiamond(cfg, d11);
  int footer   = buildStraight(cfg, diamond2, 1);
  buildConnect(cfg, diamond2, d11);
  buildConnect(cfg, diamond1, header);

  buildConnect(cfg, footer, from);
  footer = buildStraight(cfg, footer, 1);
  return footer;
}


class Havlak extends BenchmarkBase {
  final CFG cfg = new CFG();

  Havlak() : super("Havlak") {
    // Construct simple CFG.
    cfg.createNode(0);  // top
    buildBaseLoop(cfg, 0);
    cfg.createNode(1);  //s bottom
    buildConnect(cfg, 0, 2);

    // Construct complex CFG.
    var n = 2;
    for (int parlooptrees=0; parlooptrees < 10; parlooptrees++) {
      cfg.createNode(n + 1);
      buildConnect(cfg, n, n + 1);
      n = n + 1;
      for (int i=0; i < 2; ++i) {
        var top = n;
        n = buildStraight(cfg, n, 1);
        for (int j=0; j < 25; j++) {
          n = buildBaseLoop(cfg, n);
        }

        var bottom = buildStraight(cfg, n, 1);
        buildConnect(cfg, n, top);
        n = bottom;
      }
    }
  }

  void exercise() {
    LSG lsg = new LSG();
    HavlakLoopFinder finder = new HavlakLoopFinder(cfg, lsg);
    int numLoops = finder.findLoops();
    if (numLoops != 1522) {
      throw 'Wrong result - expected <1522>, but was <$numLoops>';
    }
  }

  void warmup() {
    for (int dummyloop = 0; dummyloop < 20; ++dummyloop) {
      var lsg = new LSG();
      var finder = new HavlakLoopFinder(cfg, lsg);
      finder.findLoops();
      int checksum = lsg.checksum();
      if (checksum != 435630002) {
       throw 'Wrong checksum - expected <435630002>, but was <$checksum>';
      }
    }
  }
}
