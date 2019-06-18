# RINGKASAN APP

## screnshoot

|<a href="url"><img src=screenshoot/home.gif  align="center" height="400" width="248" ></a> |<a href="url"><img src=/screenshoot/search.gif  align="center" height="400" width="248" ></a>|<a href="url"><img src=/screenshoot/detail.gif  align="center" height="400" width="248" ></a>|
|:-----------:|:--------:|:--------:|
| *dashboard* | *search* | *detail* |
</br>

|<a href="url"><img src=/screenshoot/add_meaning.gif  align="center" height="400" width="248" ></a>|<a href="url"><img src=/screenshoot/add_favorite.gif  align="center" height="400" width="248" ></a>|<a href="url"><img src=/screenshoot/delete_favorite.gif  align="center" height="400" width="248" ></a>|
|:-------------:|:-------------:|:-----------------:|
| *add_meaning* | *add_favorite*| *delete_favorite* |


## Bug
- di fragment detail,jika kata asli ter expand dan kata tambahan dari user kurang dari sembilan, maka scroll tidak akan bekerja walaupun item di recyclerview tertutupi
- ada atau tidak nya kata tambahan, kata asli tetap ter-collapse
- item terakhir recyclerview di history fragment tertutupi bottomnav
- sembunyikan bottomnav di search dan detail

## implementasi kedepannya
1. fragment detail  
    - expand kata asli jika tidak ada kata tambahan
    - tutup kata asli jika ada kata tambahan
    -  percantik dialog tambah arti kata baru