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
list = []
while i < 8571:
	movie = access.get_movie(imdbid[29])
	#print str(movie['cover url'])
	directorlist = []
	for s in movie['director']:
		director = access.search_person(s["name"])[0]
		access.update(director)
		#print director
		#print "Director: ", s
		#for k in access.search_person(str(s))[:1]:
		#	director = access.get_person(k.personID)
			#print "Movies directed by %s" % director
			#directorlist.append(str(director))
		#if s > 0 :
		#directorlist = '%s | %s' % directorbefore, director
		#else:
		#	directorlist = '%s' % director
		directorlist.append('%s' %director)
		#directorbefore = directorlist
		#print directorbefore
	directorlist[0:] = ['|'.join(directorlist[0:])]
	print directorlist
#print director
	#for d in directorlist[0:]:	
	#	list[3] = d
	#print list[3]
	list = [movieid[i], movie['rating'], movie['year'], movie['cover url']]
	list.append(directorlist[0])
	print list
	"""f = open("imdb.csv","w")
	w = csv.writer(f)  
	w.writerows(str([list])+'\n')
	print i 
	f.close() """
	i += 1
#csvlist.append(list)
#print imdbid[i]
#print movie['title']
"""print movie['year']
print movie['cover url']"""
#print csvlist

"""
f = open("imdb.csv","w")
w = csv.writer(f)  
w.writerows(csvlist)  """
f.close() 

