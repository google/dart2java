files  = "results/iv-cmp results/ii-cmp results/1a-cmp results/1b-cmp results/1c-cmp results/min-max-cmp"

set terminal png size 1680,1050
set yrange [0.1:100]
set key outside
set datafile separator ','
set logscale y

set output 'results/iv-cmp.png'
plot word(files, 1).'.csv' using 0:1, word(files, 1).'.csv' using 0:2, word(files, 1).'.csv' using 0:3

set output 'results/ii-cmp.png'
plot word(files, 2).'.csv' using 0:1, word(files, 2).'.csv' using 0:2, word(files, 2).'.csv' using 0:3

set output 'results/1a-cmp.png'
plot word(files, 3).'.csv' using 0:1, word(files, 3).'.csv' using 0:2, word(files, 3).'.csv' using 0:3

set output 'results/1b-cmp.png'
plot word(files, 4).'.csv' using 0:1

set output 'results/1c-cmp.png'
plot word(files, 5).'.csv' using 0:1

set output 'results/min-max-cmp.png'
plot word(files, 6).'.csv' using 0:1
