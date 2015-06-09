# coding=utf-8
"""
This IMDB Crawler is to GET movie
rating ,year,img_url,Director list,Actor list top 5
and based on MovieLen databsed MovieId

This is for our AI_FinalProject 
"""
import imdb
import csv
import sys
import time
from imdb import IMDb, IMDbError
#f = open('links.csv', 'r')

with open('links.csv', 'r') as csvfile:
	reader = csv.reader(csvfile)
	imdbid = [r[1] for r in reader]
with open('links.csv', 'r') as csvfile:
	reader1 = csv.reader(csvfile)
	movieid = [r1[0] for r1 in reader1]
#print imdbid[2]
#print movieid[2]
# Create the object that will be used to access the IMDb's database.
access = imdb.IMDb() # by default access the web.

i = 1
#csvlist = []
#directorbefore  = []
f = open("imdbdiscrption(1-4500).csv", 'wb')
while i < 4501:
	try:	
		movie = access.get_movie(imdbid[i])
		#print str(movie['cover url'])
		#print movie['plot outline']
		if movie.has_key('plot outline'):
			list = [movieid[i],movie['plot outline'].encode('utf-8')]
		else:
			list = [movieid[i],'']
	except (IMDbError,KeyError, IOError):
		print error
	print list

	wr = csv.writer(f)
	wr.writerow(list)
	i += 1
f.close()
