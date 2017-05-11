package com.zhiweicloud.guest.server;

import com.wyun.thrift.server.MyService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wzt on 05/05/2017.
 */
@Service
public class Server {
    public static final int SERVER_PORT = 8102;

    private final
    MyService.Iface servServiceImpl;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    public Server(MyService.Iface servServiceImpl) {
        this.servServiceImpl = servServiceImpl;
    }

    public void startServer() {
        try {
            TProcessor tprocessor = new MyService.Processor<MyService.Iface>(servServiceImpl);
            // 非阻塞异步通讯模型（服务器端）
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
            // Selector这个类，是不是很熟悉。
            serverTransport.registerSelector(Selector.open());
            //多线程半同步半异步
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.transportFactory(new TFramedTransport.Factory());
            //二进制协议
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TThreadedSelectorServer(tArgs);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
    }
}
