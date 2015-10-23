//
//  EpubUtils.m
//  
//
//  Created by Dato - horseuvn@gmail.com on 6/29/12.
//  Copyright (c) 2012 __TechMaster__. All rights reserved.
//

function appGetElementsTagNameAtPoint(x,y) {
    var tags = "";
    var e;
    var offset = 0;
    while ((tags.search(",(A|IMG),") < 0) && (offset < 20)) {
        tags = ",";
        e = document.elementFromPoint(x,y+offset);
        while (e) {
            if (e.tagName) {
                tags += e.tagName + ',';
            }
            e = e.parentNode;
        }
        if (tags.search(",(A|IMG),") < 0) {
            e = document.elementFromPoint(x,y-offset);
            while (e) {
                if (e.tagName) {
                    tags += e.tagName + ',';
                }
                e = e.parentNode;
            }
        }
        
        offset++;
    }
    return tags;
}
function appGetElementAtPoint(x,y) {
    var e;
    var offset = 0;
    while (!e && (offset < 20)) {
        e = document.elementFromPoint(x,y+offset);
        if (!e) {
            e = document.elementFromPoint(x,y-offset);
        }
        
        offset++;
    }
    return e;
}
function appGet_PositionX_ElementAtPoint(x,y){
    var e = MyAppGetElementAtPoint(x,y);
    return e.x;
}
function appGet_PositionY_ElementAtPoint(x,y){
    var e = MyAppGetElementAtPoint(x,y);
    return e.y;
}
function appGetSelectedString() {
    var text = window.getSelection();
    return text.toString();

}

function appGetLinkSRCAtPoint(x,y) {
    var tags = "";
    var e = "";
    var offset = 0;
    while ((tags.length == 0) && (offset < 20)) {
        e = document.elementFromPoint(x,y+offset);
        while (e) {
            if (e.src) {
                tags += e.src;
                break;
            }
            e = e.parentNode;
        }
        if (tags.length == 0) {
            e = document.elementFromPoint(x,y-offset);
            while (e) {
                if (e.src) {
                    tags += e.src;
                    break;
                }
                e = e.parentNode;
            }
        }
        offset++;
    }
    return tags;
}

function appGetLinkHREFAtPoint(x,y) {
    var tags = "";
    var e = "";
    var offset = 0;
    while ((tags.length == 0) && (offset < 20)) {
        e = document.elementFromPoint(x,y+offset);
        while (e) {
            if (e.href) {
                tags += e.href;
                break;
            }
            e = e.parentNode;
        }
        if (tags.length == 0) {
            e = document.elementFromPoint(x,y-offset);
            while (e) {
                if (e.href) {
                    tags += e.href;
                    break;
                }
                e = e.parentNode;
            }
        }
        offset++;
    }
    return tags;
}

function appGetPositionForAnchor_X(anchorname){
    var obj = document.getElementById(anchorname);
    var posX = obj.offsetLeft;
    while(obj.offsetParent){
        obj = obj.offsetParent;
        posX = posX + obj.offsetLeft;
    }
    return posX;
}
function appGetPositionForAnchor_Y(anchorname){
    var obj = document.getElementById(anchorname);
    var posY = obj.offsetTop;
    while(obj.offsetParent){
        obj = obj.offsetParent;
        posY = posY + obj.offsetTop;
    }
    return posY;
}