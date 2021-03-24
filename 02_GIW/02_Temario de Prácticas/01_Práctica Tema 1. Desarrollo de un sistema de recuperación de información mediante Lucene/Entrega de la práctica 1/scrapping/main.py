import urllib
from urllib.request import urlopen, urlretrieve
from alive_progress import alive_bar

from bs4 import BeautifulSoup
from pip._vendor import requests
import re

url = 'https://www.gutenberg.org/browse/scores/top'
u = urlopen(url)
try:
    html = u.read().decode('utf-8')
finally:
    u.close()

soup = BeautifulSoup(html)
lis = soup.find_all("a", href=lambda href: href and "ebooks" in href)
lis = list(filter(lambda x: (x.get('href')), lis))
lis = list(map(lambda x: (x.get('href').replace('/ebooks/', '')), lis))
lis = list(set(lis))
lis = list(filter(lambda x: (x is not None and x.isnumeric()), lis))

print('Descargando')
print('Posibles libros a descargar: ' + str(len(lis)))

i = 1

with alive_bar(total=len(lis), title='Progress') as bar:
    z = 1
    for li in lis:
        f = open('./books/' + str(z) + '-' + li+'.html', 'w')
        f.close()
        z = z + 1
    for li in lis:
        link = 'https://www.gutenberg.org/files/' + li + '/' + li + '-h/' + li + '-h.htm'
        res = False
        try:
            urlretrieve(link, './books/' + str(i) + '-' + li + '.html')
        except Exception as e:
            print('Fallo al descargar: ' + link + ' Error: ' + str(e))
            res = True
        if res:
            try:
                print('Usando alternativa de descarga 1')
                link = 'https://www.gutenberg.org/cache/epub/' + li + '/pg' + li + '.txt'
                urlretrieve(link, './books/' + str(i) + '-' + li + '.html')
                res = False
            except:
                print('Fallo al descargar (usando alternativa 1): ' + link)
                res = True
        print("Book" + str(i))
        bar()
        i = i + 1
print('Finalizado')
