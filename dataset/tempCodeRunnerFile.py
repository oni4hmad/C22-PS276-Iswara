import requests
from bs4 import BeautifulSoup
import csv

key = 'kekerasan-seksual'
url = 'https://www.detik.com/search/searchall?query={}&sortby=time&page='.format(key)
#headers = {
#    'user-agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36'
#    'server': 'cloudflare'
#}
req = requests.get(url)
print(req)
soup = BeautifulSoup(req.text, 'html.parser')
items = soup.findAll('article')
for it in items:
    name = it.find('h2', 'title').text
    link = it.a.get('href')
    halaman = requests.get(link)
    new_beautify = BeautifulSoup(halaman.text, 'lxml')
    specifik_berita = new_beautify.find('div',{'class','detail__body-text itp_bodycontent'})
    for par in specifik_berita:
        paragraf = specifik_berita.find('p').strip('</p>')
    print(name)
    print(link)
    print(paragraf)

