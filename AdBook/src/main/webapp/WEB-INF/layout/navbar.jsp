<%@page import="services.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
UserService userService = new UserService();
%>
 <nav class="navbar is-primary" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="${pageContext.request.contextPath}/">
      <img src="${pageContext.request.contextPath}/resources/img/logo.png">
      
    </a>

    <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>

  <div id="navbarBasicExample" class="navbar-menu">
    <div class="navbar-start">
      <a class="navbar-item" href="${pageContext.request.contextPath}/" >
        Home
      </a>

      <a class="navbar-item" href="${pageContext.request.contextPath}/address-book">
        Address Book
      </a>

      
    </div>

    <div class="navbar-end">
      <div class="navbar-item">
      <% if(!userService.isAuth(request)) {%>
        <div class="buttons">
          <a class="button is-primary" href="${pageContext.request.contextPath}/signup">
            <strong>Sign up</strong>
          </a>
          <a class="button is-light" href="${pageContext.request.contextPath}/login" >
            Log in
          </a>
        </div>
        <% } else {%>
        Hi ${user.name}! &nbsp;&nbsp;
        <div class="buttons">
          <a class="button is-link is-light" href="${pageContext.request.contextPath}/logout" >
            Log out
          </a>
        </div>
        <% } %>
      </div>
    </div>
  </div>
</nav>