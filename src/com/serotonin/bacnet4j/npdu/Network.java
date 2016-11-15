package com.serotonin.bacnet4j.npdu;

import com.serotonin.bacnet4j.apdu.APDU;
import com.serotonin.bacnet4j.enums.MaxApduLength;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.util.queue.ByteQueue;

abstract public class Network {
    private final int localNetworkNumber;
    private Transport transport;

    /**
     * constructor
     * */
    public Network() {
        this(0);
    }
    
    /**
     * constructor
     * */
    public Network(int localNetworkNumber) {
        this.localNetworkNumber = localNetworkNumber;
    }

    /**
     * getter
     * */
    public int getLocalNetworkNumber() {
        return localNetworkNumber;
    }

    /**
     * setter
     * */
    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    /**
     * getter
     * */
    public Transport getTransport() {
        return transport;
    }

    /**
     * similar with a "Transport" setter <br/>
     * 设置  Transport 对象 <br/>
     * ipNetwork 中覆盖了这个方法
     * */
    public void initialize(Transport transport) throws Exception {
        this.transport = transport;
    }
    
    /**
     * 获取本网络的唯一标识（由具体实现的通讯方式决定具体定义）
     * */
    abstract public NetworkIdentifier getNetworkIdentifier();

    /**
     * 返回具体通讯实现方式所支持的最大 APDU 长度
     * */
    abstract public MaxApduLength getMaxApduLength();
    
    abstract public void terminate();

    /**
     * 获取本地广播地址
     * */
    abstract public Address getLocalBroadcastAddress();

    /**
     * 获取所有的本地地址
     * */
    abstract public Address[] getAllLocalAddresses();

    abstract public void sendAPDU(Address recipient, OctetString linkService, APDU apdu, boolean broadcast)
            throws BACnetException;

    abstract public void checkSendThread();

    /**
     * NPCI - network protocol control information
     * */
    protected void writeNpci(ByteQueue queue, Address recipient, OctetString link, APDU apdu) {
        NPCI npci;
        if (recipient.isGlobal())
            npci = new NPCI((Address) null);
        else if (isLocal(recipient)) {
            if (link != null)
                throw new RuntimeException("Invalid arguments: link service address provided for a local recipient");
            npci = new NPCI(null, null, apdu.expectsReply());
        }
        else {
            if (link == null)
                throw new RuntimeException(
                        "Invalid arguments: link service address not provided for a remote recipient");
            npci = new NPCI(recipient, null, apdu.expectsReply());
        }
        npci.write(queue);
    }

    protected boolean isLocal(Address recipient) {
        int nn = recipient.getNetworkNumber().intValue();
        return nn == Address.LOCAL_NETWORK || nn == localNetworkNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + localNetworkNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Network other = (Network) obj;
        if (localNetworkNumber != other.localNetworkNumber)
            return false;
        return true;
    }
}
