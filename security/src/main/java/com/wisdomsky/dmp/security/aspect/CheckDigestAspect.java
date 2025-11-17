package com.wisdomsky.dmp.security.aspect;

import com.wisdomsky.dmp.library.common.CryptoUtility;
import com.wisdomsky.dmp.security.annotation.CheckDigest;
import com.wisdomsky.dmp.security.exception.SecurityException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckDigestAspect {

   @Around("@annotation(checkDigest)")
   public Object checkDigest(ProceedingJoinPoint joinPoint, CheckDigest checkDigest) throws Throwable {
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      String[] parameterNames = signature.getParameterNames();
      Object[] args = joinPoint.getArgs();

      String digest = null;
      String request = null;

      for (int i = 0; i < parameterNames.length; i++) {
         if (parameterNames[i].equals("digest")) {
            digest = (String) args[i];
         }
         if (parameterNames[i].equals(checkDigest.requestBodyParam())) {
            request = (String) args[i];
         }
      }

      if (digest != null && request != null) {
         String bodyDigest = CryptoUtility.sha1(request);
         if (!digest.equals(bodyDigest)) {
            throw new SecurityException(-70012, "Invalid digest");
         }
      }

      return joinPoint.proceed();
   }
}
