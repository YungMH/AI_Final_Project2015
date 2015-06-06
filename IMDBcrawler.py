# coding=UTF-8
# Import the imdb package.
import imdb
import csv
import sys

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
f = open("imdb.csv", 'wb')
while i < 8571:
	movie = access.get_movie(imdbid[i])
	#print str(movie['cover url'])
	directorlist = []
	castlist = []
	list = []
	#print movie['cast']
	
	"""list top 5 cast """
	for s in movie['cast'][:5]:
		cast = access.search_person(s["name"])[0]
		access.update(cast)
		castlist.append('%s' %cast)
	castlist[0:] = ['|'.join(castlist[0:])]
	print castlist

	"""list all the director """
	for s in movie['director']:
		director = access.search_person(s["name"])[0]
		access.update(director)
		directorlist.append('%s' %director)
	directorlist[0:] = ['|'.join(directorlist[0:])]

	list = [movieid[i], movie['rating'], movie['year'], str(movie['cover url'])]
	list.append(directorlist[0])
	list.append(castlist[0])
	print list
	wr = csv.writer(f)
	wr.writerow(list)
	i += 1
f.close()


