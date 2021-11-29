package example.chaining;

public class ChainedEx {

    public static void main(String[] args) {
        try {
            install();
        } catch (InstallException e) {
            e.printStackTrace();
        }
    }

    public static void install() throws InstallException {
        try {
            startInstall(1);
        } catch (Exception1 | Exception2 | Exception3 | Exception4 | Exception5 e) {
            InstallException ie = new InstallException("설치 중 예외발생");
            ie.initCause(e);
            throw ie;
        }
    }

    public static void startInstall(int exception)
        throws Exception1, Exception2, Exception3, Exception4, Exception5 {
        if (exception == 1) {
            throw new Exception1("예외1");
        }
        if (exception == 2) {
            throw new Exception2("예외2");
        }
        if (exception == 3) {
            throw new Exception3("예외3");
        }
        if (exception == 4) {
            throw new Exception4("예외4");
        }
        if (exception == 5) {
            throw new Exception5("예외5");
        }
    }
}

class InstallException extends Exception {

    InstallException(String msg) {
        super(msg);
    }
}

class Exception1 extends Exception {

    Exception1(String msg) {
        super(msg);
    }
}

class Exception2 extends Exception {

    Exception2(String msg) {
        super(msg);
    }
}

class Exception3 extends Exception {

    Exception3(String msg) {
        super(msg);
    }
}

class Exception4 extends Exception {

    Exception4(String msg) {
        super(msg);
    }
}

class Exception5 extends Exception {

    Exception5(String msg) {
        super(msg);
    }
}
