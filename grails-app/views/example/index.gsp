<%--
  Created by IntelliJ IDEA.
  User: jameschang
  Date: 8/22/22
  Time: 10:27 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <script type="text/javascript">
        jQuery(function() {
            jQuery("#helloInController").click(function() {
                sentMessageFromService('${createLink(controller: 'example',action: 'sentToHelloDiv2')}');
            });
        });
        function sentMessageFromService(url){
            jQuery.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                processData: false,
                contentType: false,
                success: function (json) {
                    console.log(json)
                }
            });
        }
    </script>
</head>

<body>
<button id="helloInController">helloInController</button>
</body>
</html>