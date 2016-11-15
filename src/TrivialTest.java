import java.math.BigInteger;

import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.type.primitive.Unsigned16;
import com.serotonin.util.queue.ByteQueue;

public class TrivialTest {

	public static void main(String[] args) {
		Address addr = new Address("192.168.100.2", 47808);
		ByteQueue bq = new ByteQueue();
		addr.write(bq);
	}
}

abstract class GrandPa{
	public void PRINT(){
		System.out.println("Print in grandpa");
	}
}

class Father extends GrandPa{
	@Override
	public void PRINT(){
		System.out.println("Print in father");
	}
}

class Son extends Father{
	public void sonPrint(){
		PRINT();
	}
}