package com.source.study.little.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, name = "asyncServlet", urlPatterns = { "/asyncServlet" }, initParams = {
		@WebInitParam(name = "hello", value = "world"), @WebInitParam(name = "gun", value = "dan") })
public class AsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 133332222222L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAsync(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAsync(req, resp);
	}

	private void doAsync(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		System.out.println("req.isAsyncSupported = " + req.isAsyncSupported());
		final AsyncContext ctx = req.startAsync();
		new Thread(new Runnable() {
			@Override
			public void run() {
				PrintWriter out = null;
				try {
					out = ctx.getResponse().getWriter();
					Thread.sleep(1000);
					System.out.println("Async Thread~~");
					out.write("<br>Async Write ~~~<br>");
					out.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ctx.complete();
				}
			}

		}).start();
	}
}