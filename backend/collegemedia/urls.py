"""collegemedia URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

from CollegemediaAPP import views

urlpatterns = [
    path('admin/', admin.site.urls),

    path('',views.log),
    path('log_post',views.log_post),
    path('addlib',views.addlibrarian),
    path('addlibrarian_post',views.addlibrarian_post),
    path('viewlib',views.viewlibrarian),
    path('editlibrarian/<id>', views.editlibrarian),
    path('editlibrarian_post/<id>',views.editlibrarian_post),
    path('viewbooklist', views.viewbooklist),
    path('viewfine', views.viewfine),
    path('viewstu', views.viewstudent),
    path('approvestudent', views.approvestudent),
    path('viewfeed', views.viewfeedback),
    path('changepassword', views.changepassword),
    path('changepassword_post',views.changepassword_post),
    path('viewpost',views.viewpost),
    path('viewcommunity', views.viewcommunity),
    path('home',views.home),
    path('delete/<id>',views.delete),
    path('adminapprovestudent/<id>',views.adminapprovestudent),
    path('adminrejectstudent/<id>',views.adminrejectstudent),
    path('block/<id>',views.block),
    path('unblock/<id>',views.unblock),
    path('logout',views.logout),
    path('blockpost/<id>',views.blockpost),
    path('unblockpost/<id>',views.unblockpost),


    # ##################################################################

    path('libhome',views.libhome),
    path('add_book',views.add_book),
    path('add_book_buttonclick',views.add_book_buttonclick),
    path('view_book',views.view_book),
    path('book_edit/<id>',views.book_edit),
    path('update_book_buttonclick/<id>',views.update_book_buttonclick),
    path('delete_book/<id>',views.delete_book),
    path('view_membershiprequest',views.view_membershiprequest),
    path('approve_membershiprequest/<id>',views.approve_membershiprequest),
    path('reject_membershiprequest/<id>',views.reject_membershiprequest),
    path('view_approved_membershiprequest',views.view_approved_membershiprequest),
    path('view_all_book_list/<stid>',views.view_all_book_list),
    path('update_borrow/<id>/<stid>',views.update_borrow),
    path('view_borrowed_book',views.view_borrowed_book),
    path('update_returndate/<id>',views.update_returndate),
    path('changepassword_lib',views.changepassword_lib),
    path('changepassword_post_lib',views.changepassword_post_lib),
    path('view_bookborrow_history',views.view_bookborrow_history),
    path('viewfine/<id>',views.view_fine),

    ####################################################################

    path('and_login',views.and_login),
    path('and_register', views.and_register),
    path('and_viewprofile',views.and_viewprofile),
    path('editprofile', views.editprofile),



    path('and_viewlikeandcomment', views.and_viewlikeandcomment),
    path('and_viewpostlikecomment', views.and_viewpostlikecomment),

    path('and_viewotheruserandsendrequest', views.and_viewotheruserandsendrequest),
    path('and_send_friend_request',views.and_send_friend_request),
    path('and_viewfriendrequeststatus',views.and_viewfriendrequeststatus),

    path('and_chatwithfriend',views.and_chatwithfriend),

    path('and_viewfriendrequest', views.and_viewfriendrequest),
    path('aprvfriendreq', views.aprvfriendreq),
    path('rejfriendreq', views.rejfriendreq),

    path('and_sendfeedback', views.and_sendfeedback),
    path('and_changepassword', views.and_changepassword),


    path('and_viewlibrary',views.and_view_library),
    path('and_membershipstatus',views.and_view_membership_request_status),
    path('and_libraryrequest',views.and_libraryrequest),
    path('and_viewbooks', views.and_viewbooks),
    path('and_viewhistoryofborrowedbooks', views.and_viewhistoryofborrowedbooks),
    path('and_viewreturnhistory', views.and_viewreturnhistory),
    path('and_viewfine', views.and_viewfine),

    path('and_viewuserlist',views.and_viewuserlist),
    path('and_viewotheruserpost', views.and_viewotheruserpost),
    path('view_comment_otherspost',views.view_comment_otherspost),


    path('likepost',views.likepost),
    path('addcomment',views.addcomment),

    path('and_addpost',views.and_addpost),
    path('deletepost', views.deletepost),
    path('and_viewpost', views.and_viewpost),
    path('and_editpost',views.and_editpost),
    path('and_editpost_post',views.and_editpost_post),
    path('and_viewmypostcomment',views.and_viewmypostcomment),

    path('and_communitymanagment', views.and_communitymanagment),
    path('and_deletecommunity',views.and_deletecommunity),
    path('and_editcommunity',views.and_editcommunity),
    path('editcommunitypost', views.editcommunitypost),
    path('and_viewcommunity', views.and_viewcommunity),


    path('and_add_post_in_community',views.and_add_post_in_community),
    path('and_view_community_post',views.and_view_community_post),
    path('and_edit_post_on_community',views.and_edit_post_on_community),
    path('and_edit_post_on_community_post',views.and_edit_post_on_community_post),
    path('and_delete_my_post_on_community',views.and_delete_my_post_on_community),

    path('and_add_memebers_in_community',views.and_add_memebers_in_community),
    path('and_add_members_to_community_post',views.and_add_members_to_community_post),
    path('and_view_members_in_community',views.and_view_members_in_community),
    path('remove_members_from_community',views.remove_members_from_community),

    path('and_following_list',views.and_following_list),
    path('add_chat1',views.add_chat1),
    path('view_chat1',views.view_chat1),

    path('unfollow_following',views.unfollow_following),
    path('and_follower_list',views.and_follower_list),
    path('add_chat',views.add_chat),
    path('view_chat',views.view_chat),
    path('unfollow_follower',views.unfollow_follower),

    path('and_add_chat_community',views.and_add_chat_community),
    path('and_view_chat_community',views.and_view_chat_community),

    # path('and_postmanagement',views.and_postmanagement),
    # path('and_viewcommunitymembers', views.and_viewcommunitymembers),
    # path('and_managepostcommunity', views.and_managepostcommunity),
    # path('and_viewpostcommunity', views.and_viewpostcommunity),
    # path('and_viewfriendlist',views.and_viewfriendlist),
    # path('and_sendmembershiprequestlibrary', views.and_sendmembershiprequestlibrary),
    # path('sentfriendrequest',views.sentfriendrequest),

]

