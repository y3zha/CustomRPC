package com.jerry.custom.rpc;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * server 基于 jetty
 * 1. 创建 jetty server, 监听
 * 2. 基于 ServletContextHandler 做网络处理, 需要注册到 jetty server
 * 3. ServletHolder 托管 servlet
 */
@Slf4j
public class HttpTransportServer implements TransportServer {

    private RequestHandler handler;

    private Server server;

    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            // client 发送数据过来，拿到数据
            InputStream in = req.getInputStream();

            // 返回
            OutputStream out = resp.getOutputStream();

            if (handler != null) {
                handler.onRequest(in, out);
            }
            out.flush();
        }
    }

    @Override
    public void init(int port, RequestHandler handler) {
        this.handler = handler;
        this.server = new Server(port);

        // servlet 接收请求
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        server.setHandler(servletContextHandler);

        // servlet holder 是 jetty 在处理网络请求时的抽象, 处理所有路径
        ServletHolder holder = new ServletHolder(new RequestServlet());
        servletContextHandler.addServlet(holder, "/*");
    }

    @Override
    public void start() {
        // 不让它立马返回
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}