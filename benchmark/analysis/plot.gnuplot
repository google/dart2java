# Don't change the order: files_x depends on it.
files  = "results/ii1a results/ii1b results/ii1c results/ii2 results/is results/iv1a results/iv1b results/iv1c results/iv2"
colors = "#A0000F #FF6900 #F7A500 #FFDE01 #00D45D #0BDEC3 #008EC7 #0145DE #1100A8" 
titles = "invokeinterface_type_1a invokeinterface_type_1b invokeinterface_type_1c invokeinterface_type_2 invokestatic invokevitrual_type_1a invokevitrual_type_1b invokevitrual_type_1c invokevitrual_type_2"

set terminal png size 1680,1050
set yrange [0.1:1000]
set key outside
set datafile separator ','
set logscale y

# do for [i=1:words(files)] {
#   set output word(files, i).'.png'
#   plot word(files, i).'.csv' using 0:1:3 with errorbars title word(titles, i).' pre-JIT' lc rgb word(colors, i), \
#        word(files, i).'.csv' using 0:2:4 with errorbars title word(titles, i).' post-JIT' lc rgb word(colors, i)
# }

set output 'results/all.png'

plot for [i=1:words(files)] word(files, i).'.csv' using 0:2 with lines title word(titles, i).' post-JIT' lc rgb word(colors, i)

files_iv = word(files, 6).' '.word(files, 7).' '.word(files, 8).' '.word(files, 9)
colors_iv = word(colors, 6).' '.word(colors, 7).' '.word(colors, 8).' '.word(colors, 9)
titles_iv = word(titles, 6).' '.word(titles, 7).' '.word(titles, 8).' '.word(titles, 9)

files_ii = word(files, 1).' '.word(files, 2).' '.word(files, 3).' '.word(files, 4)
colors_ii = word(colors, 1).' '.word(colors, 2).' '.word(colors, 3).' '.word(colors, 4)
titles_ii = word(titles, 1).' '.word(titles, 2).' '.word(titles, 3).' '.word(titles, 4)

files_1a = word(files, 1).' '.word(files, 5).' '.word(files, 6)
colors_1a = word(colors, 1).' '.word(colors, 5).' '.word(colors, 6)
titles_1a = word(titles, 1).' '.word(titles, 5).' '.word(titles, 6)

files_1b = word(files, 2).' '.word(files, 7)
colors_1b = word(colors, 2).' '.word(colors, 7)
titles_1b = word(titles, 2).' '.word(titles, 7)

files_1c2 = word(files, 3).' '.word(files, 4).' '.word(files, 8).' '.word(files, 9)
colors_1c2 = word(colors, 3).' '.word(colors, 4).' '.word(colors, 8).' '.word(colors, 9)
titles_1c2 = word(titles, 3).' '.word(titles, 4).' '.word(titles, 8).' '.word(titles, 9)


set output 'results/iv.png'
plot for [i=1:words(files_iv)] word(files_iv, i).'.csv' using 0:1 with lines title word(titles_iv, i).' pre-JIT' lc rgb word(colors_iv, i), \
    for [i=1:words(files_iv)] word(files_iv, i).'.csv' using 0:2 with lines title word(titles_iv, i).' post-JIT' lc rgb word(colors_iv, i)


set output 'results/ii.png'
plot for [i=1:words(files_ii)] word(files_ii, i).'.csv' using 0:1 with lines title word(titles_ii, i).' pre-JIT' lc rgb word(colors_ii, i), \
    for [i=1:words(files_ii)] word(files_ii, i).'.csv' using 0:2 with lines title word(titles_ii, i).' post-JIT' lc rgb word(colors_ii, i)


set output 'results/1a.png'
plot for [i=1:words(files_1a)] word(files_1a, i).'.csv' using 0:1 with lines title word(titles_1a, i).' pre-JIT' lc rgb word(colors_1a, i), \
    for [i=1:words(files_1a)] word(files_1a, i).'.csv' using 0:2 with lines title word(titles_1a, i).' post-JIT' lc rgb word(colors_1a, i)


set output 'results/1b.png'
plot for [i=1:words(files_1b)] word(files_1b, i).'.csv' using 0:1 with lines title word(titles_1b, i).' pre-JIT' lc rgb word(colors_1b, i), \
    for [i=1:words(files_1b)] word(files_1b, i).'.csv' using 0:2 with lines title word(titles_1b, i).' post-JIT' lc rgb word(colors_1b, i)


set output 'results/1c2.png'
plot for [i=1:words(files_1c2)] word(files_1c2, i).'.csv' using 0:1 with lines title word(titles_1c2, i).' pre-JIT' lc rgb word(colors_1c2, i), \
    for [i=1:words(files_1c2)] word(files_1c2, i).'.csv' using 0:2 with lines title word(titles_1c2, i).' post-JIT' lc rgb word(colors_1c2, i)

