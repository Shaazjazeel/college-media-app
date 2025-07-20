from django.db import models

class login(models.Model):
    username=models.CharField(max_length=100)
    password=models.CharField(max_length=100)
    usertype= models.CharField(max_length=100)

class librarian(models.Model):
    name= models.CharField(max_length=100)
    age = models.CharField(max_length=100)
    place = models.CharField(max_length=100)
    gender = models.CharField(max_length=100)
    post = models.CharField(max_length=100)
    pin = models.CharField(max_length=100)
    email = models.CharField(max_length=100)
    phone = models.CharField(max_length=100)
    image = models.CharField(max_length=100,default=1)
    LOGIN=models.ForeignKey(login, on_delete=models.CASCADE)

class student(models.Model):
    name = models.CharField(max_length=100)
    age = models.CharField(max_length=100)
    gender = models.CharField(max_length=100)
    place = models.CharField(max_length=100)
    registerno = models.CharField(max_length=100)
    image = models.CharField(max_length=100)
    department = models.CharField(max_length=100)
    LOGIN = models.ForeignKey(login, on_delete=models.CASCADE)
    year = models.CharField(max_length=100)
    bio = models.CharField(max_length=100)



class post(models.Model):
    post = models.CharField(max_length=100)
    title= models.CharField(max_length=100)
    description=models.CharField(max_length=100)
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    status= models.CharField(max_length=100)

class book(models.Model):
    name = models.CharField(max_length=100)
    author = models.CharField(max_length=100)
    image = models.CharField(max_length=100)
    totalpage = models.CharField(max_length=100)
    price = models.CharField(max_length=100)
    LIBRARIAN = models.ForeignKey(librarian, on_delete=models.CASCADE)

class feedback(models.Model):
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    feedback = models.CharField(max_length=100)
    date = models.CharField(max_length=100)

class community(models.Model):
    name = models.CharField(max_length=100)
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)

class communitymembers(models.Model):
    COMMUNITY = models.ForeignKey(community, on_delete=models.CASCADE)
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)

class communitypost(models.Model):
    COMMUNITY = models.ForeignKey(community, on_delete=models.CASCADE)
    post = models.CharField(max_length=100)
    date = models.CharField(max_length=100)

class bookborrow(models.Model):
    BOOK = models.ForeignKey(book, on_delete=models.CASCADE)
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    borrow_date = models.CharField(max_length=100)
    due_date = models.CharField(max_length=100)
    returned_date = models.CharField(max_length=100)
    status = models.CharField(max_length=100)

class fine(models.Model):
    amount = models.CharField(max_length=100)
    extra_days = models.CharField(max_length=100)
    BOOK_BORROW= models.ForeignKey(bookborrow, on_delete=models.CASCADE)
    status = models.CharField(max_length=100)

class like(models.Model):
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    POST= models.ForeignKey(post, on_delete=models.CASCADE)
    like=models.CharField(max_length=100)

class comment(models.Model):
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    POST= models.ForeignKey(post, on_delete=models.CASCADE)
    comment=models.CharField(max_length=100)
    date=models.CharField(max_length=100)

class friend_request(models.Model):
    status = models.CharField(max_length=100)
    req_date= models.CharField(max_length=100)
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    sender = models.ForeignKey(student, on_delete=models.CASCADE,related_name="sender")

class chat(models.Model):
    message=models.CharField(max_length=100)
    type=models.CharField(max_length=100)
    LOGIN = models.ForeignKey(login, on_delete=models.CASCADE)
    FRIEND = models.ForeignKey(friend_request, on_delete=models.CASCADE)
    message_date=models.CharField(max_length=100)

class library_membership(models.Model):
    req_date=models.CharField(max_length=100)
    STUDENT = models.ForeignKey(student, on_delete=models.CASCADE)
    LIBRARIAN = models.ForeignKey(librarian, on_delete=models.CASCADE)
    status = models.CharField(max_length=100)


class community_chat(models.Model):
    LOGIN = models.ForeignKey(login, on_delete=models.CASCADE)
    COMMUNITY = models.ForeignKey(community, on_delete=models.CASCADE)
    date=models.CharField(max_length=200)
    chat=models.CharField(max_length=200)













