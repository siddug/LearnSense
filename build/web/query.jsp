<%-- 
    Document   : query
    Created on : 4 Aug, 2013, 4:20:46 AM
    Author     : varun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        
        <title>Carousel Template for Bootstrap</title>
        
        <!-- Bootstrap core CSS -->
        <link href="bootstrap.css" rel="stylesheet">
        
        <!-- Custom styles for this template -->
        <link href="carousel.css" rel="stylesheet">
        <link href="signin.css" rel="stylesheet">
    </head>
    <body>
        <%
        System.out.println("Entered");
        int num_keys = (Integer)session.getAttribute("Num_keys");
        String querystring,actualquery="";
        if(session.getAttribute("queryword")!=null){
            querystring = session.getAttribute("queryword").toString();
            actualquery = querystring;
        }
        else querystring="";
        for(int i=0;i<num_keys;i++){
            System.out.println("Entered"+i);
            String Keyword = session.getAttribute("Keyword"+i).toString();
            querystring+=" "+Keyword;
            //out.write("<p>"+Keyword+"</p>");
        }
        System.out.println(querystring);
        String text_content = session.getAttribute("Text_Content").toString();
        //out.write("<p>"+session.getAttribute("Text_Content").toString()+"</p>");
        
        String meaning="";
        if(session.getAttribute("Meaning")!=null){
            meaning = session.getAttribute("Meaning").toString();
            //out.write("<p>"+meaning+"</p>");
        }
        %>                
        
        <div class="navbar-wrapper" style="display:none;">
            <div class="container">
          
                <div class="navbar navbar-inverse navbar-static-top">
                    <div class="container">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">Project name</a>
                        <div class="nav-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li class="active"><a href="#">Home</a></li>
                                <li><a href="#about">About</a></li>
                                <li><a href="#contact">Contact</a></li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Action</a></li>
                                        <li><a href="#">Another action</a></li>
                                        <li><a href="#">Something else here</a></li>
                                        <li class="divider"></li>
                                        <li class="nav-header">Nav header</li>
                                        <li><a href="#">Separated link</a></li>
                                        <li><a href="#">One more separated link</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            
            </div>
        
        </div>
        
        <div >
            <table align="center" width="1200px" height="500px">
             <tr>
            <td width="350px"><div id="img1" ></div></td>
            <td width="350px"><div id="img2" ></div></td>
            <td width="350px"><div id="img3" ></div></td>
             </tr>
            </table>
        </div>
 
             <div class="container">
                <div class="carousel-caption">
                    <h1><%= actualquery%></h1>
                    <p><%= meaning%></p>
                    <!--<p><a class="btn btn-large btn-primary" href="#">Sign up today</a></p>-->
                </div>
            </div>
        
        <div class="container marketing" >

            <form method="get" action="LearnSense" style="height:100px; margin-top:30px;margin-bottom:0px;">
                <input type="text" name="query" class="form-control" placeholder="Enter a specific word from text to get it's sense'" autofocus style="width:90%;float:left" >
                <input name="qtype" hidden="true" value="query"/>
                <button class="btn btn-primary " type="submit" style="width:10%;float:right">Search</button>
            </form>





        <!-- START THE FEATURETTES -->

        <!--<hr class="featurette-divider"style="display:none">-->

            <div class="featurette" style="margin-top:0px;padding-top:0px">
                <img class="featurette-image img-circle pull-right" data-src="holder.js/512x512" style="display:none;margin-top:0px">
                <h2 class="featurette-heading" style="margin-top:0px;float:left;margin-left:0px;margin-right:0px">"<span class="text-muted"></span></h2>
                <h2 class="featurette-heading" style="margin-top:0px;float:right;margin-left:0px;margin-right:0px">"<span class="text-muted"></span></h2><hr/>
                <p class="lead"style="margin-top:0px;margin-left:0px;margin-right:0px;float:center"><%= text_content%></p>

            </div>

        <hr class="featurette-divider" style="display:none">

        <div class="featurette"style="display:none">
            <img class="featurette-image img-circle pull-left" data-src="holder.js/512x512">
            <h2 class="featurette-heading">Oh yeah, it's that good. <span class="text-muted">See for yourself.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        </div>

        <hr class="featurette-divider" style="display:none">

        <div class="featurette"style="display:none">
            <img class="featurette-image img-circle pull-right" data-src="holder.js/512x512">
            <h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        </div>

        <hr class="featurette-divider">

        <!-- /END THE FEATURETTES -->


        <!-- FOOTER -->
        <footer>
            <p class="pull-right"><a href="#">Back to top</a></p>
            <p style="display:none">&copy; 2013 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
        </footer>

    </div>
                <script>
                    var theUrl = "http://query.yahooapis.com/v1/public/yql?q=select * from boss.search where q='<%= querystring%>' and service = 'images' and ck='dj0yJmk9YWF3ODdGNWZPYjg2JmQ9WVdrOWVsWlZNRk5KTldFbWNHbzlNVEEyTURFNU1qWXkmcz1jb25zdW1lcnNlY3JldCZ4PTUz' and secret='a3d93853ba3bad8a99a175e8ffa90a702cd08cfa'&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";                
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
                        
                        var image_div = document.getElementById("img"+i);
                        var img1 = document.createElement("img");
                        img1.setAttribute("src", url3);
                        img1.setAttribute("widht", "300");
                        img1.setAttribute("height", "300")
                        console.log(url3+"\n");
                        image_div.appendChild(img1);
                    }                   
                </script>
              
        
 
                  
     </body>
</html>
