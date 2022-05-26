#from email import header
import requests
from bs4 import BeautifulSoup
import csv

key = input('please enter the term :')
location = input('please enter the location too :')
location = 'hotels'
location = 'london'
url = 'https://www.yell.com/ucs/UcsSearchAction.do?keywords={}&location={}&scrambleSeed=1050191601&pageNum='.format(key, location)
headers = {
    'user-agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36'
#    'accept' : 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange,v=b3,q=0.9'
}
datas = []
count_page = 0 
for page in range(1, 11):
    count_page += 1
    print('scrapping page :', count_page)
    req = requests.get(url+str(page), headers=headers)
    #print(req)
    soup = BeautifulSoup(req.text, 'html.parser')
    items = soup.findAll('div', 'row businessCapsule--mainRow')
    for it in items:
        name = it.find('h2', 'businessCapsule--name text-h2').text
        address = ''.join(it.find('span', {'itemprop':'address'}).text.strip().split('\n')) #strip().split('\n') untuk hapus enter dsb
        try : website = it.find('a', {'rel':'nofollow noopener'})['href'].replace('https://', "").replace('wwww.', "").split('/')[0]
        except : web = ''
        try : telp = it.find('span', 'business--telephoneNumber').text
        except : telp =''
        image = it.find('div', 'col-sm-4 col-md-4 col-lg-5 businessCapsule--leftSide').find('img')['data-original']
        if 'http' not in image:
            image = 'https://www.yell.com{}'.format(image)
        datas.append([name, address, web, telp, image])

head = ['Name', 'Address', 'Website', 'Phone Number', 'Image URL']
write = csv.writer(open('results/{}_{}.csv'.format(key, location), 'w', newline=''))
write.writerow(head)
for d in datas: writer.writerow(d)         

