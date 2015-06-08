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
f = open("imdb(1-8571).csv", 'wb')
while i < 8572:
	flag = False
	while not flag:
		try:
			print "trying %s" %imdbid[i]
			#for _ in range(5):
			#	try:
			movie = access.get_movie(imdbid[i])
			#		break
			#	except (KeyError,IMDbDataAccessError,IOError):
			#		print "IMDbDataAccessError"
			#		sleep(5)
			#print str(movie['cover url'])
			directorlist = []
			castlist = []
			list = []
			#print movie['cast']
			#title = movie['title'].encode('utf-8')
			#print title
			"""list top 5 cast """
			#for z in range(0,100):
			#while True:
				#try:
			for s in movie['cast'][:5]:
				#print s 
				cast = access.search_person(s["name"])[0]
				#print cast
				access.update(cast)
				#print cast
				#cast = cast.decode('utf-8')
				#print type(cast)
				castlist.append(u'%s' %cast)
				#castlist.append(str(cast))
			#print castlist
			castlist[0:] = ['|'.join(castlist[0:])]
			#print castlist
					#break
				#except (IOError,KeyError):
				#	print "except"
				#break
			"""list all the director """
			for s in movie['director']:
				director = access.search_person(s["name"])[0]
				access.update(director)
				directorlist.append(u'%s' %director)
			directorlist[0:] = ['|'.join(directorlist[0:])]
			#print directorlist

			""" test movie url """
			#print movie
			if movie.has_key('cover url'):
				list = [movieid[i], movie['rating'], movie['year'], str(movie['cover url'])]
			else:
				list = [movieid[i], movie['rating'], movie['year'], '']

			list.append(directorlist[0].encode('utf-8'))
			list.append(castlist[0].encode('utf-8'))
			flag = True
			break
		except(IOError,KeyError):
			print "exception"
	print list
	wr = csv.writer(f)
	wr.writerow(list)
	i += 1
f.close()