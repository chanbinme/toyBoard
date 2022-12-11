package hobin.toyBoard.exception;

import lombok.Getter;

public class BussinessLogicException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public BussinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
