import datetime
import random
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

from django.core.files.storage import FileSystemStorage
from django.db.models import Q
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render

# Create your views here.
from CollegemediaAPP.models import *


def log(request):
    return render(request,'index.html')

def log_post(request):
    request.session['lg'] = 1
    username1=request.POST['textfield']
    password1=request.POST['textfield2']
    res=login.objects.filter(username=username1,password=password1)
    if res.exists():
        res=res[0]
        request.session['lid']=res.id
        if res.usertype=="admin":
            return HttpResponse("<script>alert('LOGIN SUCESS');window.location='/home'</script>")
        elif res.usertype=="librarian":
            return HttpResponse("<script>alert('LOGIN SUCESS');window.location='/libhome'</script>")
        return HttpResponse("<script>alert('NOT FOUND');window.location='/'</script>")
    return HttpResponse("<script>alert('NOT EXIST');window.location='/'</script>")


def addlibrarian(request):
    request.session['head'] = "ADD LIBRARIAN"
    return render(request,'admin/addlibrarian.html')



def addlibrarian_post(request):
    name1 = request.POST['textfield']
    age1=request.POST['textfield2']
    gender1=request.POST['RadioGroup1']
    place1=request.POST['textfield3']
    post1=request.POST['textfield4']
    pin1=request.POST['textfield5']
    email1=request.POST['textfield6']
    phn1=request.POST['textfield7']
    pc=request.FILES['filefield']
    d=datetime.datetime.now().strftime("%Y%m%d%H%M%S")
    fs=FileSystemStorage()
    fs.save(r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\"+d+'.jpg',pc)
    path="/static/image/"+d+'.jpg'
    a=login.objects.filter(usertype='librarian',username=email1)
    if a.exists():
        return HttpResponse("already exist")

    obj = login()
    obj.username = email1
    obj.usertype = 'librarian'
    obj.password = random.randint(0000,99999)
    obj.save()
    obj1 = librarian()
    obj1.name= name1
    obj1.age= age1
    obj1.gender= gender1
    obj1.place= place1
    obj1.post= post1
    obj1.pin= pin1
    obj1.image= path
    obj1.email= email1
    obj1.phone= phn1
    obj1.LOGIN_id = obj.id
    obj1.save()

    pwd= obj.password

    import smtplib
    s = smtplib.SMTP(host='smtp.gmail.com', port=587)
    s.starttls()
    s.login("jazeelshaaz@gmail.com", "jmyr cihx lzgw daye")
    msg = MIMEMultipart()  # create a message.........."
    msg['From'] = "jazeelshaaz@gmail.com"
    msg['To'] = email1
    msg['Subject'] = "Your Password for Smart Donation Website"
    body = "Your Password is:- - " + str(pwd)
    msg.attach(MIMEText(body, 'plain'))
    s.send_message(msg)

    return HttpResponse("<script>alert('Sucess');window.location='/viewlib'</script>")


def viewlibrarian(request):
    request.session['head'] = "VIEW LIBRARIAN"
    data= librarian.objects.all()
    return render(request,'admin/viewlibrarian.html',{'view':data})

def editlibrarian(request,id):
    data = librarian.objects.get(id = id)
    return render(request,'admin/editlibrarian.html',{'view':data})

def editlibrarian_post(request,id):
    try:
        request.session['head'] = "EDIT LIBRARIAN"
        name1 = request.POST['textfield']
        age1 = request.POST['textfield2']
        gender1 = request.POST['RadioGroup1']
        place1 = request.POST['textfield3']
        post1 = request.POST['textfield4']
        pin1 = request.POST['textfield5']
        phn1 = request.POST['textfield7']
        pc = request.FILES['filefield']
        d = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
        fs = FileSystemStorage()
        fs.save(
            r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\" + d + '.jpg',
            pc)
        path = "/static/image/" + d + '.jpg'
        librarian.objects.filter(id=id).update(name=name1, age=age1, gender=gender1, place=place1, post=post1, pin=pin1,
                                               phone=phn1, image=path)

        return HttpResponse("<script>alert('Sucess');window.location='/viewlib'</script>")
    except Exception as e:
        request.session['head'] = "EDIT LIBRARIAN"
        name1 = request.POST['textfield']
        age1 = request.POST['textfield2']
        gender1 = request.POST['RadioGroup1']
        place1 = request.POST['textfield3']
        post1 = request.POST['textfield4']
        pin1 = request.POST['textfield5']
        phn1 = request.POST['textfield7']
        librarian.objects.filter(id=id).update(name=name1, age=age1, gender=gender1, place=place1, post=post1, pin=pin1,phone=phn1)
        return HttpResponse("<script>alert('Sucess');window.location='/viewlib'</script>")


def viewbooklist(request):
    request.session['head'] = "VIEW BOOKLIST"
    data = book.objects.all()
    return render(request, 'admin/viewbooklist.html', {'view': data})

def viewfine(request):
    request.session['head'] = "VIEW FINE"
    data= fine.objects.all()
    return render(request,'admin/viewfine.html',{'view':data})

def viewstudent(request):
    request.session['head'] = "VIEW STUDENT"
    data = student.objects.filter(LOGIN__usertype='pending')
    return render(request,'admin/viewstudent.html',{'view':data})

def approvestudent(request):
    request.session['head'] = "APPROVED STUDENT"
    data= student.objects.filter(LOGIN__usertype__in=('student','blocked'))
    return render(request,'admin/approvestudent.html',{'view':data})

def viewcommunity(request):
    request.session['head'] = "VIEW COMMUNITY"
    data = community.objects.all()
    return render(request, 'admin/viewcommunity.html', {'view': data})

def viewpost(request):
    request.session['head'] = "VIEW POST"
    data= post.objects.all()
    return render(request,'admin/viewpost.html',{'view':data})

def viewfeedback(request):
    request.session['head'] = "VIEW FEEDBACK"
    data= feedback.objects.all()
    return render(request,'admin/viewfeedback.html',{'view':data})

def changepassword(request):
    request.session['head'] = "CHANGE PASSWORD"
    return render(request,'admin/changepassword.html')

def changepassword_post(request):
    currentpass1=request.POST['textfield']
    newpass1=request.POST['textfield2']
    confirmpass1=request.POST['textfield3']
    res=login.objects.filter(password=currentpass1,id=request.session['lid'])
    if res.exists():
        if newpass1==confirmpass1:
            login.objects.filter(id=request.session['lid']).update(password=newpass1)
            return HttpResponse("<script>alert('LOGIN SUCESS');window.location='/'</script>")
        return HttpResponse("<script>alert('PASSWORD MISMATCH');window.location='/changepassword'</script>")
    return HttpResponse("<script>alert('NOT FOUND');window.location='/changepassword'</script>")


def home(request):
    request.session['head'] = "ADMIN HOME"
    return render(request,'admin/new_index.html')

def delete(request,id):
    login.objects.get(id = id).delete()
    return HttpResponse("<script>alert('Sucess');window.location='/viewlib'</script>")


def adminapprovestudent(request,id):
    login.objects.filter(id = id).update(usertype = 'student')
    return HttpResponse("<script>alert('Approved sucessfully');window.location='/viewstu'</script>")

def adminrejectstudent(request,id):
    login.objects.filter(id = id).update(usertype = 'rejected')
    return HttpResponse("<script>alert('Rejected');window.location='/home'</script>")

def block(request,id):
    login.objects.filter(id = id).update(usertype = 'blocked')
    return HttpResponse("<script>alert('Blocked succesfully');window.location='/home'</script>")


def unblock(request,id):
    login.objects.filter(id = id).update(usertype = 'student')
    return HttpResponse("<script>alert('Unblocked successfully');window.location='/home'</script>")


def logout(request):
    request.session['lg'] = 0
    return HttpResponse("<script>alert('LOGOUT SUCESSFULL');window.location='/'</script>")

def blockpost(request,id):
    post.objects.filter(id = id).update(status = 'blocked')
    return HttpResponse("<script>alert('Blocked succesfully');window.location='/home'</script>")

def unblockpost(request,id):
    post.objects.filter(id = id).update(status = 'approved')
    return HttpResponse("<script>alert('Unblocked succesfully');window.location='/home'</script>")

#=====================================================================================================================================================================


def libhome(request):
    request.session['head'] = "LIBRARY HOME"

    return render(request,'librarian/new_index.html')

def add_book(request):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "ADD BOOK"

    return render(request,"librarian/add_book.html")

def add_book_buttonclick(request):
    bn=request.POST['Book_Name']

    auth=request.POST['Author']
    image=request.FILES['image']
    d=datetime.datetime.now().strftime("%Y%m%d-%H%M%S")
    fs=FileSystemStorage()
    fs.save(r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\"+d+'.jpg',image)
    path='/static/image/'+d+'.jpg'
    totalpage = request.POST['pages']
    price=request.POST['price']
    i=book.objects.filter(name=bn,author=auth)
    if i.exists():
        return HttpResponse("<script>alert('Already existed');window.location='/add_book#aaa'</script>")
    else:
        obj = book()
        obj.name = bn
        obj.totalpage=totalpage
        obj.author = auth
        obj.price = price
        obj.image = path
        obj.LIBRARIAN = librarian.objects.get(LOGIN=request.session['lid'])
        obj.save()
        return HttpResponse("<script>alert('Added successfully');window.location='/view_book#aaa'</script>")


def view_book(request):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW BOOK"
    res=book.objects.all()
    return render(request,"librarian/view_book.html",{'data':res})

def book_edit(request,id):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "UPDATE BOOK"

    res=book.objects.get(id=id)
    return render(request,"librarian/update_book.html",{'data':res})

def update_book_buttonclick(request,id):
    try:
        bn = request.POST['Book_Name']

        auth = request.POST['Author']
        image = request.FILES['image']
        d = datetime.datetime.now().strftime("%Y%m%d-%H%M%S")
        fs = FileSystemStorage()
        fs.save(r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\" + d + '.jpg', image)
        path = '/static/image/' + d + '.jpg'
        totalpage = request.POST['pages']
        price = request.POST['price']
        book.objects.filter(id=id).update(name=bn,image=path,totalpage=totalpage,author=auth,price=price)
        return HttpResponse("<script>alert('Updated');window.location='/view_book#aaa'</script>")
    except Exception as e:
        bn = request.POST['Book_Name']

        auth = request.POST['Author']


        totalpage = request.POST['pages']
        price = request.POST['price']
        book.objects.filter(id=id).update(name=bn, totalpage=totalpage, author=auth, price=price)
        return HttpResponse("<script>alert('Updated');window.location='/view_book#aaa'</script>")

def delete_book(request,id):
    book.objects.get(id=id).delete()
    return HttpResponse("<script>alert('deleted successfully');window.location='/view_book#aaa'</script>")

def view_membershiprequest(request):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW MEMBERSHIP REQUEST"
    res=library_membership.objects.filter(LIBRARIAN__LOGIN=request.session['lid'],status='request')
    return render(request,"librarian/view memebershiprequest.html",{'data':res})

def approve_membershiprequest(request,id):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "Aprove"
    res=library_membership.objects.filter(id=id).update(status='approved')
    return HttpResponse("<script>alert('approved successfully');window.location='/view_membershiprequest#aaa'</script>")

def reject_membershiprequest(request,id):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "reject"
    res=library_membership.objects.filter(id=id).update(status='rejected')
    return HttpResponse("<script>alert('rejected successfully');window.location='/view_membershiprequest#aaa'</script>")



def view_approved_membershiprequest(request):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW APPROVED REQUEST"
    res=library_membership.objects.filter(LIBRARIAN__LOGIN=request.session['lid'],status='approved')
    return render(request,"librarian/view approvedmemebershiprequest.html",{'data':res})


def view_all_book_list(request,stid):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW BOOK"
    res=book.objects.filter(LIBRARIAN__LOGIN=request.session['lid'])
    return render(request,"librarian/view_book_studentborrow.html",{'data':res,'stid':stid})




def update_borrow(request, id,stid):
    b = datetime.datetime.now()
    due_date = b + datetime.timedelta(days=10)
    due_date_str = due_date.strftime("%d-%m-%Y")
    data = bookborrow()
    data.BOOK_id = id
    data.STUDENT_id=stid
    data.borrow_date = b.strftime("%d-%m-%Y")
    data.status = 'borrowed'
    data.returned_date = 'pending'
    data.due_date = due_date_str
    data.save()

    return HttpResponse("<script>alert('update successfully');window.location='/view_membershiprequest#aaa'</script>")

def view_borrowed_book(request):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW BORROWED BOOK"

    res=bookborrow.objects.filter(BOOK__LIBRARIAN__LOGIN=request.session['lid'],status='borrowed')
    return render(request,"librarian/view borrowed_book.html",{'data':res})



import datetime
from datetime import timedelta


def update_returndate(request, id):
    d = datetime.datetime.now()  #
    d_str = d.strftime("%d-%m-%Y")


    record = bookborrow.objects.get(id=id)
    due_date = datetime.datetime.strptime(record.due_date, "%d-%m-%Y")
    res = bookborrow.objects.filter(id=id).update(returned_date=d_str, status='returned')


    fine_amount = 0
    extra_days = 0

    if d > due_date:
        extra_days = (d - due_date).days
        fine_amount = extra_days * 1

    fine_data = fine()
    fine_data.BOOK_BORROW_id = record.id
    fine_data.amount = fine_amount
    fine_data.extra_days = extra_days
    fine_data.status = 'pending'
    fine_data.save()

    return HttpResponse("<script>alert('update successfully');window.location='/view_membershiprequest#aaa'</script>")


def changepassword_lib(request):
    request.session['head'] = "CHANGE PASSWORD"
    return render(request,'librarian/changepassword.html')

def changepassword_post_lib(request):
    currentpass1=request.POST['textfield']
    newpass1=request.POST['textfield2']
    confirmpass1=request.POST['textfield3']
    res=login.objects.filter(password=currentpass1,id=request.session['lid'])
    if res.exists():
        if newpass1==confirmpass1:
            login.objects.filter(id=request.session['lid']).update(password=newpass1)
            return HttpResponse("<script>alert('LOGIN SUCESS');window.location='/'</script>")
        return HttpResponse("<script>alert('PASSWORD MISMATCH');window.location='/changepassword'</script>")
    return HttpResponse("<script>alert('NOT FOUND');window.location='/changepassword'</script>")


def view_bookborrow_history(request):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW HISTORY"
    res=bookborrow.objects.filter(BOOK__LIBRARIAN__LOGIN=request.session['lid'])
    return render(request,"librarian/view bookborrow_historyt.html",{'data':res})

def view_fine(request,id):
    if "lid" not in request.session:
        return HttpResponse("<script>alert('session expired...Login again');window.location='/'</script>")
    request.session['head'] = "VIEW HISTORY"
    res=fine.objects.filter(BOOK_BORROW_id=id)
    return render(request,"librarian/viewfine.html",{'data':res})






#######################################################################

def and_login(request):
    un=request.POST['unm']
    ps=request.POST['pss']
    data=login.objects.filter(username=un,password=ps)
    if data.exists():
        ldata=data[0]
        lid=ldata.id
        type=ldata.usertype

        return JsonResponse({"status":'ok',"lid":lid,"type":type})


    return JsonResponse({"status": 'none'})

# ----------------------------profile----------------------------------------

def and_viewprofile(request):
    lid = request.POST['lid']
    res = student.objects.get(LOGIN=lid)

    return JsonResponse({"status":"ok","name":res.name,"age":res.age,"gender":res.gender,"place":res.place,"regno":res.registerno,"image":res.image,"depart":res.department,"year":res.year,"bio":res.bio})



def editprofile(request):
    try:
        stuid = request.POST['lid']
        name = request.POST['name']
        gender = request.POST['gender']
        bio = request.POST['bio']
        year = request.POST['year']
        dp = request.POST['depart']
        rg = request.POST['reg']
        ag = request.POST['age']
        plc = request.POST['place']
        pic = request.FILES['pic']
        d = datetime.datetime.now().strftime("%d%m%Y-%H%M%S")
        fs = FileSystemStorage()
        fs.save(
            r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\" + d + '.jpg',
            pic)
        path = "/static/image/" + d + '.jpg'

        student.objects.filter(LOGIN=stuid).update(name=name, gender=gender, bio=bio, year=year, department=dp,
                                                registerno=rg, age=ag, place=plc, image=path)
        return JsonResponse({"status": 'ok'})
    except Exception as e:
        stuid = request.POST['lid']
        name = request.POST['name']
        gender = request.POST['gender']
        bio = request.POST['bio']
        year = request.POST['year']
        dp = request.POST['depart']
        rg = request.POST['reg']
        ag = request.POST['age']
        plc = request.POST['place']
        student.objects.filter(LOGIN=stuid).update(name=name, gender=gender, bio=bio, year=year, department=dp,
                                                registerno=rg, age=ag, place=plc)
        return JsonResponse({"status": 'ok'})


# ---------post management-------------------

def and_addpost(request):
    lid =request.POST['lid']
    im=request.FILES['pic']
    til=request.POST['title']
    des=request.POST['description']
    d=datetime.datetime.now().strftime("%Y%m%d-%H%M%S")
    fs = FileSystemStorage()
    fs.save(r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\post\\"+d+'.jpg',im)
    path = "/static/post/"+d+".jpg"

    data=post()
    data.post=path
    data.title=til
    data.description=des
    data.status='approve'
    data.STUDENT_id = student.objects.get(LOGIN=lid).id
    data.save()

    return JsonResponse({"status":'ok'})

def and_viewpost(request):
    lid=request.POST['lid']
    data=post.objects.filter(STUDENT__LOGIN= lid)
    li=[]
    for i in data:
        data1 = like.objects.filter(POST=i.id)
        c = data1.count()
        data2=comment.objects.filter(POST=i.id)
        d = data2.count()
        li.append({
                'id':i.id,
                'title':i.title,
                'post':i.post,
                'description':i.description,
                "like":c,
                "comment":d,
            })
    return JsonResponse({"status":'ok','data':li})

def deletepost(request):
    pid=request.POST['pid']
    post.objects.get(id = pid).delete()
    return JsonResponse({"status":'ok'})


def and_editpost(request):
    pid = request.POST['pid']
    res = post.objects.get(id=pid)

    return JsonResponse({"status":"ok","title":res.title,"description":res.description,"image":res.post})



def and_editpost_post(request):
    pid=request.POST['pid']
    title=request.POST['title']
    description=request.POST['description']
    # bio=request.POST['bio']


    if 'pic' in request.FILES:
        pic=request.FILES['pic']
        d=datetime.datetime.now().strftime("%d%m%Y-%H%M%S")
        fs=FileSystemStorage()
        fs.save(r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\"+d+'.jpg',pic)
        path="/static/image/"+d+'.jpg'

        student.objects.filter(id=pid).update(title=title,description=description,image=path)
        return JsonResponse({"status": 'ok'})

    else:
        student.objects.filter(id=pid).update(title=title,description=description)
        return JsonResponse({"status": 'ok'})


def and_viewmypostcomment(request):
    psid=request.POST['psid']
    data=comment.objects.filter(POST_id=psid)
    li = []
    for i in data:
        li.append({
            'cmdid': i.id,
            'date': i.date,
            'name': i.STUDENT.name,
            'image': i.STUDENT.image,
            'comment':i.comment
        })

    return JsonResponse({"status": 'ok', 'data': li})


# -------------------------------------------------------------------------
#
# def and_postmanagement(request):
#
#     return JsonResponse({"status":'ok'})

def and_viewlikeandcomment(request):
    psid = request.POST['psid']


    data = comment.objects.filter(POST_id=psid)
    li=[]
    for i in data:
        li.append({
            'id':i.id,
            'name':i.STUDENT.name,
            'date':i.date,
            'comment':i.comment,
        })

    return JsonResponse({"status":'ok',"data":li})

# def and_viewotheruserandsendrequest(request):
#
#     data= student.objects.filter()
#     li=[]
#     for i in data:
#         li.append({
#             'id':i.id,
#             'name':i.name,
#             'department':i.department,
#             'image':i.image,
#             'gender':i.gender,
#             'age':i.age
#         })
#
#
#     return JsonResponse({"status":'ok'})


# ---------------view other student and and send request----------------------------------
def and_viewotheruserandsendrequest(request):
    lid = request.POST['lid']
    print(lid, "ooo")

    obj = student.objects.filter(~Q(LOGIN=lid))
    ar = []

    for i in obj:
        stid = i.id

        dd = friend_request.objects.filter(sender__LOGIN=lid, STUDENT=stid)

        if not dd.exists():
            ar.append({
                "ouid": i.id,
                "name": i.name,
                "image": i.image,
                "department": i.department,
            })

        print(ar)

    return JsonResponse({"status": 'ok', "data": ar})

def and_send_friend_request(request):
    lid=request.POST['lid']
    ouid=request.POST['ouid']
    data=friend_request()
    data.req_date=datetime.datetime.now().strftime("%d-%m-%Y")
    data.status='request_sended'
    data.STUDENT_id=ouid
    data.sender_id=student.objects.get(LOGIN=lid).id
    data.save()
    return JsonResponse({"status":"ok"})

def and_viewfriendrequeststatus(request):
    lid = request.POST['lid']
    data = friend_request.objects.filter(sender__LOGIN=lid)
    ar = []
    for i in data:
        ar.append({
            #     req_id,name,image,email,department,status;
            "req_id": i.id,
            "name": i.STUDENT.name,
            "image": i.STUDENT.image,

            "department": i.STUDENT.department,
            "status": i.status,
        })

    return JsonResponse({"status":'ok',"data":ar})

# following
def and_following_list(request):
    lid = request.POST['lid']
    data = friend_request.objects.filter(sender__LOGIN=lid,status='approved')
    ar = []
    for i in data:
        ar.append({
            #     req_id,name,image,email,department,status;
            "following_id": i.id,
            "name": i.STUDENT.name,
            "image": i.STUDENT.image,

            "status": i.status,
        })

    return JsonResponse({"status": 'ok', "data": ar})

def add_chat1(request):
    lid = request.POST['lid']
    print("llid", lid)  # login_id student_id
    toid = request.POST['following_id']  # follower_id sender_id
    m = request.POST['message']
    print(m,"lll")
    print(m,"toid")

    d = datetime.datetime.now().strftime("%Y-%m-%d")
    # t = datetime.datetime.now().strftime("%H:%M:%S")


    s_id = friend_request.objects.get(id=toid)
    std_id = student.objects.get(LOGIN=lid).id


    obj = chat()
    obj.message_date = d
    obj.type = "student"
    obj.FRIEND_id = toid
    obj.LOGIN_id = lid
    obj.message = m
    obj.save()

    return JsonResponse({'status': "Inserted"})


def view_chat1(request):
    lid = request.POST['lid']
    print(lid,"uuu")
    toid = request.POST['following_id']
    print('ttttid',toid)
    lastid = request.POST['lastid']
    print(lid,toid,lastid)
    # res=chat.objects.filter(STUDENT=student.objects.get(LOGIN=lid))
    res=chat.objects.filter(Q(LOGIN=lid),Q(FRIEND=toid),Q(id__gt=lastid))
    print(res)
    ar=[]
    for i in res:
        ar.append({
            "id":i.id,
            "date":i.message_date,
            "userid":i.FRIEND.id,
            "sid":i.type,
            "chat":i.message,
        })
    print(ar,"arrrrrrrrrrr")
    return JsonResponse({'status':"ok",'data':ar})

def unfollow_following(request):
    following_id=request.POST['following_id']
    friend_request.objects.filter(id=following_id).delete()
    return JsonResponse({"status": 'ok'})

# view others request and approve reject------------------------------------------------
def and_viewfriendrequest(request):
    lid=request.POST['lid']
    data= friend_request.objects.filter(STUDENT__LOGIN=lid)
    li=[]
    for i in data:
        li.append({
            "id":i.id,
            'name': i.sender.name,
            'department': i.sender.department,
            'image': i.sender.image,

        })
        print(li)
    return JsonResponse({"status":'ok',"data":li})


def aprvfriendreq(request):
    reqid=request.POST['reqid']
    friend_request.objects.filter(id = reqid).update(status = 'approved')
    return JsonResponse({"status":'ok'})

def rejfriendreq(request):
    reqid=request.POST['reqid']
    friend_request.objects.filter(id = reqid).update(status = 'rejected')
    return JsonResponse({"status":'ok'})

def and_follower_list(request):
    lid=request.POST['lid']
    data= friend_request.objects.filter(STUDENT__LOGIN=lid,status = 'approved')
    li=[]
    for i in data:
        li.append({
            "follower_id":i.id,
            'name': i.sender.name,

            'image': i.sender.image,
            "senderid":i.sender.id

        })
        print(li)
    return JsonResponse({"status":'ok',"data":li})


def add_chat(request):
    lid = request.POST['lid']
    print("llid", lid)  # login_id student_id
    toid = request.POST['follower_id']  # follower_id sender_id
    m = request.POST['message']
    print(m,"lll")
    print(m,"toid")

    d = datetime.datetime.now().strftime("%Y-%m-%d")
    # t = datetime.datetime.now().strftime("%H:%M:%S")


    s_id = friend_request.objects.get(id=toid)
    std_id = student.objects.get(LOGIN=lid).id


    obj = chat()
    obj.message_date = d
    obj.type = "student"
    obj.FRIEND_id = toid
    obj.LOGIN_id = lid
    obj.message = m
    obj.save()

    return JsonResponse({'status': "Inserted"})


def view_chat(request):
    lid = request.POST['lid']
    toid = request.POST['follower_id']
    print('ttttid',toid)
    lastid = request.POST['lastid']
    print(lid,toid,lastid)
    # res=chat.objects.filter(STUDENT=student.objects.get(LOGIN=lid))Q(RECEIVERUSER=uid,SENDERUSER=user.objects.get(LOGIN=lid)) | Q(SENDERUSER=uid,RECEIVERUSER=user.objects.get(LOGIN=lid)),Q(id__gt=lastid)
    res=chat.objects.filter(Q(LOGIN=lid),Q(FRIEND_id=toid),Q(id__gt=lastid))
    print(res)
    ar=[]
    for i in res:
        ar.append({
            "id":i.id,
            "date":i.message_date,
            "userid":i.FRIEND.STUDENT.LOGIN.id,
            "sid":i.type,
            "chat":i.message,
        })
    print(ar,"arrrrrrrrrrr")
    return JsonResponse({'status':"ok",'data':ar})

def unfollow_follower(request):
    follower_id=request.POST['follower_id']
    friend_request.objects.filter(id=follower_id).delete()
    return JsonResponse({"status": 'ok'})

# ----------------------------------------------------------------------------------------------------



# def and_viewfriendlist(request):
#
#     data= friend_request.objects.all()
#     li=[]
#     for i in data:
#         li.append({
#             'id':i.id,
#             'name': i.STUDENT.name,
#             'department': i.STUDENT.department,
#             'image': i.STUDENT.image,
#         })

    #
    # return JsonResponse({"status":'ok',"data":li})

def and_chatwithfriend(request):
    return JsonResponse({"status":'ok'})



def and_viewpostlikecomment(request):
    data= post.objects.filter()
    data1 = like.objects.all()
    li=[]
    for i in data:

        li.append({
            'id': i.id,
            'name':i.STUDENT.post,
            'post':i.post,
            'title':i.title,
            'description':i.description,

        })
    return JsonResponse({"status":'ok'})



# def and_viewcommunitymembers(request):
#     return JsonResponse({"status":'ok'})

# def and_managepostcommunity(request):
#     return JsonResponse({"status":'ok'})

# def and_viewpostcommunity(request):
#     return JsonResponse({"status":'ok'})

# def and_sendmembershiprequestlibrary(request):
#     return JsonResponse({"status":'ok'})

def and_viewbooks(request):
    me_id=request.POST['me_id']
    v=library_membership.objects.get(id=me_id)
    print(v)
    s=v.LIBRARIAN_id
    print(s)
    lib_id=request.POST['lib_id']
    print(lib_id,"lib")
    data=book.objects.filter(LIBRARIAN_id=s)
    ar=[]
    for i in data:
        ar.append({

            "book_id":i.id,
            "bname":i.name,
            "bimage":i.image,
            "bauthor":i.author,
            "bprice":i.price,
            "bpages":i.totalpage,


        })
        print(ar)
    return JsonResponse({"status":'ok',"data":ar})

def and_viewhistoryofborrowedbooks(request):
    lid=request.POST['lid']
    data=bookborrow.objects.filter(STUDENT__LOGIN=lid,status='borrowed')
    ar=[]
    for i in data:
        ar.append({

            "bb_id":i.id,
            "bb_image":i.BOOK.image,
            "bb_name":i.BOOK.name,
            "bb_author":i.BOOK.author,
            "bb_date":i.borrow_date,
            "bbdue_date":i.due_date,
            "bb_status":i.status
        })
    return JsonResponse({"status":'ok',"data":ar})

def and_viewreturnhistory(request):
    lid = request.POST['lid']
    data = bookborrow.objects.filter(STUDENT__LOGIN=lid,status='returned')
    ar = []
    for i in data:
        ar.append({

            "rb_id": i.id,
            "rb_image": i.BOOK.image,
            "rb_name": i.BOOK.name,
            "rb_author": i.BOOK.author,
            "rb_date": i.borrow_date,
            "rbdue_date": i.due_date,
            "rb_return_date":i.returned_date,
            "rb_status": i.status
        })
        print(ar)
    return JsonResponse({"status":'ok',"data":ar})

def and_viewfine(request):
    lid=request.POST['lid']
    data=fine.objects.filter(BOOK_BORROW__STUDENT__LOGIN=lid)
    ar=[]
    for i in data:
        ar.append({
            "fine_id":i.id,
            "f_lib":i.BOOK_BORROW.BOOK.LIBRARIAN.name,
            "f_image":i.BOOK_BORROW.BOOK.image,
            "f_name":i.BOOK_BORROW.BOOK.name,
            "f_author":i.BOOK_BORROW.BOOK.author,
            "f_bdate":i.BOOK_BORROW.borrow_date,
            "f_ddate":i.BOOK_BORROW.due_date,
            "f_rdate":i.BOOK_BORROW.returned_date,
            "f_days":i.extra_days,
            "f_status":i.status,
            "f_amount":i.amount
        })
    return JsonResponse({"status":'ok',"data":ar})
# ==========================================================================
def and_sendfeedback(request):
    fb=request.POST['fedback']
    lid = request.POST['lid']

    data = feedback()
    data.feedback =fb
    data.STUDENT_id = student.objects.get(LOGIN=lid).id
    data.date = datetime.datetime.now()
    data.save()
    return JsonResponse({"status": 'ok'})

def and_changepassword(request):
    oldpass=request.POST['oldpas']
    cnfpass=request.POST['cnfpas']
    nwpass=request.POST['newpass']

    lid = request.POST['lid']

    data = login.objects.filter(id = lid,password=oldpass)
    if data.exists():
        if cnfpass == nwpass:
            login.objects.filter(id = lid).update(password = cnfpass)
            return JsonResponse({"status": 'ok'})
        else:
            return JsonResponse({"status": None})
    else:
        return JsonResponse({"status": None})



def and_register(request):
    name=request.POST['nme']
    username=request.POST['usrnm']
    ps=request.POST['pass']
    cnfpass=request.POST['cnfps']
    dp=request.POST['depart']
    rg=request.POST['regno']
    ag=request.POST['age']
    plc=request.POST['place']
    g=request.POST['gender']
    pic=request.FILES['pic']
    bio=request.POST['bio']
    year=request.POST['year']
    print(ps,"ttttt")
    print(cnfpass,"uuuu")
    print(dp,"dep")
    print(g,"eeet")
    d = login.objects.filter(username=username)
    if d.exists():
        return JsonResponse({"status": None})

    else:
        if ps == cnfpass:
            d = datetime.datetime.now().strftime("%Y%m%d-%H%M%S")
            fs = FileSystemStorage()
            fs.save(r"C:\Users\shazj\Desktop\COLLEGE_MEDIA\New folder\collegemedia\CollegemediaAPP\static\image\\" + d + '.jpg', pic)
            path = "/static/image/" + d + ".jpg"

            l=login()
            l.username=username
            l.password=ps
            l.usertype='pending'
            l.save()
            obj=student()
            obj.name=name
            obj.department=dp
            obj.registerno=rg
            obj.age=ag
            obj.bio=bio
            obj.year=year
            obj.image = path
            obj.place=plc
            obj.gender=g
            obj.LOGIN_id = l.id
            obj.save()
            return JsonResponse({"status":"ok"})
        else:
            return JsonResponse({"status": None})

def and_viewcommunity(request):
    data=community.objects.all()
    li=[]
    for i in data:
        li.append({
            'id':i.id,
            'comname':i.name,

        })
        print(li)

    return JsonResponse({"status":'ok',"data":li})





def and_viewotheruserpost(request):
    lid=request.POST['lid']
    data=post.objects.filter(~Q(STUDENT__LOGIN=lid),status = 'approved')
    li=[]
    for i in data:
        data1 = like.objects.filter(POST=i.id)
        c = data1.count()
        data2 = comment.objects.filter(POST=i.id)
        d = data2.count()
        li.append({
            'id': i.id,
            'name': i.STUDENT.name,
            'post': i.post,
            'title':i.title,
            'description':i.description,
            "like": c,
            "comment":d

            })
        print(li)
    return JsonResponse({"status":'ok','data':li})

def view_comment_otherspost(request):
    pid=request.POST['pid']
    data=comment.objects.filter(POST_id=pid)
    li = []
    for i in data:
        li.append({
            'cmdid': i.id,
            'date': i.date,
            'name': i.STUDENT.name,
            'image': i.STUDENT.image,
            'comment':i.comment
        })

    return JsonResponse({"status": 'ok', 'data': li})


def and_libraryrequest(request):
    lib_id = request.POST['lib_id']
    lid = request.POST['lid']

    data = library_membership()
    data.req_date = datetime.datetime.now().strftime("%d-%m-%Y")
    data.status = 'request'
    data.LIBRARIAN_id = lib_id
    data.STUDENT = student.objects.get(LOGIN=lid)
    data.save()
    return JsonResponse({"status":'ok'})

def and_likepost(request):
    return JsonResponse({"status":'ok'})
# ==========================================================================

# library

def and_view_library(request):

    data=librarian.objects.filter(LOGIN__usertype='librarian')
    ar=[]
    for i in data:
        ar.append(
            {
                "lib_id":i.id,

                "name":i.name,
                "place":i.place,
                "email":i.email,
                "phone":i.phone,
            }
        )
        print(ar)
    return JsonResponse({"status":"ok","data":ar})

def and_view_membership_request_status(request):
    lid=request.POST['lid']
    data=library_membership.objects.filter(STUDENT__LOGIN=lid)
    ar=[]
    for i in data:
    #  me_id,date,library,lib_name,place,status;
        ar.append({
            "me_id":i.id,
            "date":i.req_date,

            "lib_name":i.LIBRARIAN.name,
            "place":i.LIBRARIAN.place,
            "status":i.status

        })

        print(ar)
    return JsonResponse({"status": 'ok',"data":ar})

def and_viewuserlist(request):
    data = student.objects.all()
    li = []
    for i in data:
        li.append({
            "id": i.id,
            'name': i.name,
            'department': i.department,
            'image': i.image,
            'year': i.year

        })

    return JsonResponse({"status": 'ok', "data": li})


# def sentfriendrequest(request):
#     lid = request.POST['lid']
#     stid=request.POST['stid']
#     st=friend_request()
#     st.req_date=datetime.datetime.now()
#     st.STUDENT_id=stid
#     st.sender_id=student.objects.get(LOGIN=lid).id
#     st.status='pending'
#     st.save()
#     return JsonResponse({"status":'ok'})

# =======================================community========================================
def and_communitymanagment(request):
    comname = request.POST['comname']
    lid=request.POST['lid']

    data=community()
    data.name=comname
    data.STUDENT=student.objects.get(LOGIN=lid)
    data.save()

    return JsonResponse({"status":'ok'})

def editcommunitypost(request):
    comname = request.POST['comname']
    com_id = request.POST['com_id']

    community.objects.filter(id=com_id).update(name=comname)


    return JsonResponse({"status": 'ok'})


def and_editcommunity(request):
    com_id = request.POST['com_id']
    print(com_id,"aaaaaaaaa")
    com = community.objects.get(id=com_id)
    return JsonResponse({"status": 'ok',"name":com.name})

def and_deletecommunity(request):
    cid=request.POST['com_id']
    community.objects.get(id = cid).delete()

    return JsonResponse({"status":'ok'})

# =============================================================================================
def likepost(request):
    pid=request.POST['pid']
    lid=request.POST['lid']
    lp=like()
    lp.like=1
    lp.POST_id = pid
    lp.STUDENT_id=student.objects.get(LOGIN=lid).id
    lp.save()
    return JsonResponse({"status":'ok'})



def addcomment(request):
    co=request.POST['addcom']
    lid = request.POST['lid']
    pid=request.POST['pid']
    ac = comment()
    ac.POST_id=pid
    ac.comment =co
    ac.date = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S")
    ac.STUDENT_id = student.objects.get(LOGIN=lid).id
    ac.save()
    return JsonResponse({"status": 'ok'})





# =====================================add post on community==========================================

def and_add_post_in_community(request):
    com_id=request.POST['com_id']
    comm_post=request.POST['comm_post']

    obj=communitypost()
    obj.post=comm_post
    obj.date=datetime.datetime.now().strftime("%d-%m-%Y")
    obj.COMMUNITY_id=com_id
    obj.save()
    return JsonResponse({"status":'ok'})

def and_view_community_post(request):        #view community post added by community
    comm_id=request.POST['com_id']
    print(comm_id)
    obj=communitypost.objects.filter(COMMUNITY_id=comm_id)
    ar=[]
    for i in obj:
        ar.append(
            {
                "pcm_id":i.id,
                "date":i.date,
                "post":i.post
            }
        )
    print(ar)
    return JsonResponse({"status":'ok',"data":ar})

def and_edit_post_on_community(request):
    pcm_id = request.POST['pcm_id']
    data = communitypost.objects.get(id=pcm_id)
    return JsonResponse({"status": "ok",
                         "community_post":data.post

                         })

def and_edit_post_on_community_post(request):
    lid = request.POST['lid']
    pcm_id = request.POST['pcm_id']
    print(pcm_id)
    obj = communitypost.objects.get(id=pcm_id)
    print(pcm_id, 'uuuu')
    lo = student.objects.get(LOGIN=lid)
    v = request.POST['post']

    data = communitypost.objects.filter(id=pcm_id).update(post=v)

    return JsonResponse({"status": "ok"})

def and_delete_my_post_on_community(request):
    pcm_id = request.POST['pcm_id']
    communitypost.objects.get(id=pcm_id).delete()
    return JsonResponse({'status': "ok"})


#============================================= community member management==================================

def and_add_memebers_in_community(request):
    lid=request.POST['lid']
    obj=student.objects.filter(~Q(LOGIN=lid))
    ar = []
    for i in obj:
        ar.append(
            {
                "cm_id": i.id,
                "cm_name": i.name,
                "cm_image":i.image

            }
        )
    print(ar)

    return JsonResponse({"status":'ok',"data":ar})

def and_add_members_to_community_post(request):
    cm_id=request.POST['cm_id']
    print(cm_id)

    com_id=request.POST['com_id']
    data = communitymembers.objects.filter(STUDENT=cm_id)
    if data.exists():
        return JsonResponse({"status": 'notok'})
    else:
        print(com_id)
        data=communitymembers()
        data.COMMUNITY_id=com_id
        data.STUDENT_id=cm_id
        data.save()
        return JsonResponse({"status": 'ok'})

def and_view_members_in_community(request):
    com_id = request.POST['com_id']
    obj = communitymembers.objects.filter(COMMUNITY_id=com_id)
    ar = []
    for i in obj:
        ar.append(
            {
                "cms_id": i.id,
                "name": i.STUDENT.name,
                "image": i.STUDENT.image

            }
        )
    print(ar)
    return JsonResponse({"status": 'ok',"data":ar})

def remove_members_from_community(request):
    cms_id=request.POST['cms_id']
    communitymembers.objects.get(id=cms_id).delete()
    return JsonResponse({"status": 'ok'})

# =========================================================================================================6

def and_add_chat_community(request):
    msg=request.POST['message']
    cid=request.POST['cid']
    lid=request.POST['lid']
    obj=community_chat()
    obj.chat=msg
    obj.COMMUNITY_id=cid
    obj.LOGIN_id= lid
    obj.date=datetime.datetime.now().strftime("%Y-%m-%d")
    obj.save()
    return JsonResponse({"status":"Inserted"})




# def and_view_chat_community(request):
#     lid=request.POST['lid']
#     print(lid,"liddddddddddddd")
#     cid=request.POST['cid']
#     print(cid,"cidddddddddddd")
#     sid = communitymembers.objects.get(COMMUNITY=cid,STUDENT__LOGIN =lid).COMMUNITY.id
#
#     li = []
#     lastid=request.POST['lastid']
#     # me = chat.objects.filter(Q(COMMUNITY=cid,LOGIN_id=user.objects.get(LOGIN=lid)) | Q(SENDERUSER=uid,RECEIVERUSER=user.objects.get(LOGIN=lid)),Q(id__gt=lastid))
#     me = community_chat.objects.filter(Q(COMMUNITY=cid,LOGIN=lid) | Q(LOGIN=lid,COMMUNITY=cid),Q(id__gt=lastid))
#     for i in me:
#         li.append({
#             "id": i.id,
#             "sid": i.LOGIN.id,
#             "date": i.date,
#             "chat": i.chat
#         })
#     for i in li:
#         print(i['sid'])
#     return JsonResponse({"status":"ok","data":li})




def and_view_chat_community(request):
    lid = request.POST.get('lid')
    print(lid, "liddddddddddddd")
    cid = request.POST.get('cid')
    print(cid, "cidddddddddddd")
    lastid = request.POST.get('lastid', 0)

    try:
        community_member = communitymembers.objects.get(COMMUNITY=cid, STUDENT__LOGIN=lid)
        sid = community_member.COMMUNITY.id
    except communitymembers.DoesNotExist:
        return JsonResponse({"status": "error", "message": "Community member not found"})

    li = []

    chats = community_chat.objects.filter(
        Q(COMMUNITY=cid, LOGIN__LOGIN=lid) | Q(LOGIN__LOGIN=lid, COMMUNITY=cid), Q(id__gt=lastid)
    )


    print("chattttt",chats)

    for chat in chats:
        if sid == chat.COMMUNITY.id:
            li.append({
                "id": chat.id,
                "sid": chat.LOGIN.id,
                "date": chat.date.strftime("%Y-%m-%d %H:%M:%S"),
                "chat": chat.chat
            })
            print("liiiiiiiiiiiiiiiiiiiii",li)
    for item in li:
        print(item['sid'])




    return JsonResponse({"status": "ok", "data": li})