package sync.demo;

public class TestRunner {
    public static void main(String[] args) {
        // 뮤텍스 테스트 실행
        MutexTest mutexTest = new MutexTest();
        mutexTest.runTest();

        // 세마포어 테스트 실행
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        semaphoreTest.runTest();

        // 모니터 테스트 실행 (단일 대기 스레드)
        MonitorTest monitorTest = new MonitorTest();
        monitorTest.runTest();

        // 모니터 테스트 실행 (다중 대기 스레드)
        monitorTest.runTestWithMultipleWaiters();
    }
}