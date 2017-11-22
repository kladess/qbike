package club.newtech.qbike.order.domain.core.vo;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


@Getter
@ToString
public class IntentionVo implements Delayed {
    /**
     * Base of nanosecond timings, to avoid wrapping
     */
    private static final long ORIGIN = System.currentTimeMillis();
    private final long time;
    private String customerId;
    private String startPoint;
    private String endPoint;

    public IntentionVo(String customerId, String startPoint, String endPoint, long time) {
        this.customerId = customerId;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.time = time;
    }

    final static long now() {
        return System.currentTimeMillis() - ORIGIN;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(time - now(), TimeUnit.MILLISECONDS);
        return d;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) // compare zero ONLY if same object
            return 0;
        if (other instanceof IntentionVo) {
            IntentionVo x = (IntentionVo) other;
            long diff = time - x.time;
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
        }
        long d = (getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
}
