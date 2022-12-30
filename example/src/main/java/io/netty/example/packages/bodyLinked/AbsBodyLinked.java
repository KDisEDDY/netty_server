package io.netty.example.packages.bodyLinked;

import java.nio.ByteBuffer;

public abstract class AbsBodyLinked {
    protected AbsBodyLinked next = null;

    protected int thisLinkedLength = 0;

    public AbsBodyLinked(AbsBodyLinked next, int thisLinkedLength) {
        this.next = next;
        this.thisLinkedLength = thisLinkedLength;
    }

    public AbsBodyLinked(int thisLinkedLength) {
        this.thisLinkedLength = thisLinkedLength;
    }

    public void setNextLinked(AbsBodyLinked link) {
        this.next = link;
    }

    public AbsBodyLinked next(){
        return next;
    }
    /**
     * 连接下一个数据区
     * @param byteBuffer
     * @return 是否有下一个，true有，false没有
     */
    public abstract boolean link(ByteBuffer byteBuffer) throws Exception;

    public int getTotalLength(){
        int result = thisLinkedLength;
        if(next != null){
            result += next.getTotalLength();
        }
        return result;
    }

}
