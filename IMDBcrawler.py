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
csvlist = []
directorlist = []
#while i < 8571:
movie = access.get_movie(imdbid[i])
#print str(movie['cover url'])
for s in movie['director']:
	#print "Director: ", s
	for k in access.search_person(str(s))[:1]:
		director = access.get_person(k.personID)
		#print "Movies directed by %s" % director
		directorlist.append(str(director))
	#print directorlist
#print director	
list = [movieid[i], movie['rating'], movie['year'], directorlist, str(movie['cover url'])]
csvlist.append(list)
#print imdbid[i]
#print movie['title']
"""print movie['year']
print movie['cover url']"""
print csvlist
i += 1

f = open("imdb.csv","w")
w = csv.writer(f)  
w.writerows(csvlist)  
f.close() 

