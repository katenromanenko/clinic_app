package by.katenromanenko.clinicapp.config.aop;

import by.katenromanenko.clinicapp.config.annotation.LoginAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoginAspect {

    @Pointcut("execution(* by.katenromanenko.clinicapp..controller..*(..))")
    public void anyControllerMethod() {}

    @Pointcut("@annotation(loginAnnotation)")
    public void annotated(LoginAnnotation loginAnnotation) {}

    @Before("anyControllerMethod()")
    public void beforeControllerCall(org.aspectj.lang.JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("[CTRL] -> {} args={}", method, Arrays.toString(args));
    }

    @Around("anyControllerMethod()")
    public Object aroundControllerCall(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        String method = pjp.getSignature().toShortString();
        try {
            Object result = pjp.proceed();
            long took = System.currentTimeMillis() - start;
            log.info("[CTRL] <- {} tookMs={}", method, took);
            return result;
        } catch (Throwable ex) {
            long took = System.currentTimeMillis() - start;
            log.error("[CTRL] !! {} tookMs={} error={}", method, took, ex.toString());
            throw ex;
        }
    }

    @Around("annotated(loginAnnotation)")
    public Object aroundAnnotated(ProceedingJoinPoint pjp, LoginAnnotation loginAnnotation) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        String method = sig.getDeclaringType().getSimpleName() + "." + sig.getName();

        try {
            Object result = pjp.proceed();
            long took = System.currentTimeMillis() - start;
            log.info("[ANN] <- {} tookMs={}", method, took);
            return result;
        } catch (Throwable ex) {
            long took = System.currentTimeMillis() - start;
            log.error("[ANN] !! {} tookMs={} error={}", method, took, ex.toString(), ex);
            throw ex;
        }
    }
}
