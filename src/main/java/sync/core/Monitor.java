package sync.core;

/**
 * Monitor 클래스는 동기화를 위한 모니터 패턴을 구현합니다.
 * 스레드 간 상호 배제를 보장하고, 특정 조건이 충족될 때까지 스레드가 대기하도록 합니다.
 */
public class Monitor {
    private final Object lock = new Object(); // 동기화를 위한 락 객체
    private int conditionCount = 0; // 스레드가 기다리는 조건 카운터

    /**
     * 조건이 충족될 때까지 스레드를 대기 상태로 전환합니다.
     * @throws InterruptedException 스레드가 인터럽트되면 발생
     */
    public void waitForCondition() throws InterruptedException {
        synchronized (lock) {
            while (conditionCount <= 0) {
                lock.wait(); // 조건이 만족되지 않으면 대기
            }
            conditionCount--; // 조건을 하나 소비
        }
    }

    /**
     * 조건을 만족시키고, 대기 중인 스레드 하나를 깨웁니다.
     */
    public void signalCondition() {
        synchronized (lock) {
            conditionCount++;
            lock.notify(); // 대기 중인 스레드 하나를 깨움
        }
    }

    /**
     * 조건을 만족시키고, 대기 중인 모든 스레드를 깨웁니다.
     */
    public void signalAllCondition(int count) {
        synchronized (lock) {
            conditionCount += count;
            lock.notifyAll(); // 대기 중인 모든 스레드를 깨움
        }
    }
}