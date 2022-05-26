library(rvest)
library(dplyr)
library(stringr)

scrape_detik = function(url){
  data = 
    url %>% 
    read_html() %>% 
    {tibble(
      headline = html_nodes(.,".detail__title") %>% 
        html_text() %>% 
        str_squish(),
      tanggal = html_nodes(.,".detail__date") %>% 
        html_text(),
      teks = html_nodes(.,"p") %>% 
        html_text() %>% 
        str_squish() %>% 
        paste(collapse = " ")
    )}
  return(data)
}
scrape_detik('https://hot.detik.com/movie/d-6081008/bintang-doctor-strange-dipenjara-8-tahun-atas-pelecehan-seksual-anak')

