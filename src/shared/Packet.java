package shared;

import java.io.Serializable;

/**
 * Marker interface that covers objects to send it through ObjectOutputStream.
 * <p><strang>Made it serializable to transform objects into bytes for use on server</strang>
 **/
public interface Packet extends Serializable {
}