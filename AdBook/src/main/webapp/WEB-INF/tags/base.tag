<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/layout/header.jsp"></jsp:include>
  <body>
  <jsp:include page="/WEB-INF/layout/navbar.jsp"></jsp:include>
  
    <div id="body" class="container is-fluid">
      <jsp:doBody/>
    </div>

</body>
</html>