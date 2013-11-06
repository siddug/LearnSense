/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
window.onload=function (e)
{
var theUrl = "http://query.yahooapis.com/v1/public/yql?q=select * from boss.search where q='zimbabwe' and service = 'images' and ck='dj0yJmk9YWF3ODdGNWZPYjg2JmQ9WVdrOWVsWlZNRk5KTldFbWNHbzlNVEEyTURFNU1qWXkmcz1jb25zdW1lcnNlY3JldCZ4PTUz' and secret='a3d93853ba3bad8a99a175e8ffa90a702cd08cfa'&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
var mapurl = "http://query.yahooapis.com/v1/public/yql?q=select * from geo.placefinder where text='fuck'"
var xmlHttp = null;
xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", theUrl,  false );
xmlHttp.send( null );
xmlDoc=xmlHttp.responseXML;
//xmlDoc=xmlHttp.responseText;


for(var i=1;i<=3;i++){
    var url1=xmlDoc.getElementsByTagName("clickurl")[i];
    var url2=url1.childNodes[0];
    var url3=url2.nodeValue;

    var image_div = document.getElementById("images");
    var img1 = document.createElement("img");
    img1.setAttribute("src", url3);
    image_div.appendChild(img1);
}

/*
var url1=xmlDoc.getElementsByTagName("country")[0];
var url2=url1.childNodes[0];
var url3=url2.nodeValue;
*/
//alert(xmlDoc);
return xmlHttp.responseText;
}

