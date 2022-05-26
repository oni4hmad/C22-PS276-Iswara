import requests
from bs4 import BeautifulSoup
import csv

key = 'kekerasan-seksual'
url = 'https://www.detik.com/search/searchall?query={}&sortby=time&page='.format(key)
#headers = {
#    'user-agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36'
#    'server': 'cloudflare'
#}
datas = []
req = requests.get(url)
print(req)
soup = BeautifulSoup(req.text, 'html.parser')
items = soup.findAll('article')
for it in items:
    name = it.find('h2', 'title').text
    link = it.a.get('href')
    #link = it.find('a', {'rel':'nofollow noopener'})['href']
    datas.append([name, link])
    
head = ['Name', 'URL']
write = csv.writer(open('detik url dataset.csv'.format(key), 'w', newline=''))
write.writerow(head)
for d in datas: 
    write.writerow(d)        