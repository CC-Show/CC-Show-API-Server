package com.boxfox.util.vertx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.net.HttpHeaders;
import io.vertx.core.Vertx;
import org.reflections.Reflections;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/* boxfox 2017.02.13*/

public class RouteRegister {
    private List<RouterContext> routerList;
    private Router router;

    public static RouteRegister routing(Vertx vertx) {
        return new RouteRegister(Router.router(vertx));
    }

    private RouteRegister(Router router) {
        this.router = router;
        this.routerList = new ArrayList();
    }

    private void route(String... pacakge) {
        Reflections routerAnnotations = new Reflections(pacakge);
        Set<Class<?>> annotatedClass = routerAnnotations.getTypesAnnotatedWith(RouteRegistration.class);
        Set<Method> annotatedMethod = routerAnnotations.getMethodsAnnotatedWith(RouteRegistration.class);

        for (Class<?> c : annotatedClass) {
            RouteRegistration annotation = c.getAnnotation(RouteRegistration.class);
            try {
                Object routingInstance = c.newInstance();
                Handler handler = (Handler<RoutingContext>) routingInstance;
                for (HttpMethod method : annotation.method())
                    router.route(method, annotation.uri()).handler(handler);
                routerList.add(new RouterContext(annotation, routingInstance));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Method m : annotatedMethod) {
            RouteRegistration annotation = m.getAnnotation(RouteRegistration.class);
            try {
                Object instance = searchCreatedInstance(m.getDeclaringClass());
                if (instance == null)
                    instance = m.getDeclaringClass().newInstance();
                Handler handler = createMethodHandler(instance, m);
                for (HttpMethod method : annotation.method())
                    router.route(method, annotation.uri()).handler(handler);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler<RoutingContext> createMethodHandler(Object instance, Method m) {
        return new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext ctx) {
                List<Object> argments = new ArrayList<>();
                Arrays.stream(m.getParameters()).forEach(param -> {
                    String paramType = param.getAnnotation(Param.class).type();
                    String paramName = param.getName();
                    Class<?> paramClass = param.getType();
                    if (paramClass.equals(Handler.class)) {
                        argments.add(ctx);
                    } else {
                        String paramData = null;
                        switch (paramType) {
                            case Param.TYPE_BODY:
                                paramData = getParameterFromBody(ctx, paramName, paramClass);
                                break;
                            case Param.TYPE_PATH:
                                paramData = ctx.pathParam(paramName);
                                break;
                            default:
                                paramData = ctx.queryParam(paramName).get(0);
                        }
                        argments.add(paramData);
                    }
                });
                try {
                    m.invoke(instance, argments.toArray());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private String getParameterFromBody(RoutingContext ctx, String paramName, Class<?> paramType) {
        String paramData = null;
        /*boolean json = ctx.request().getHeader(HttpHeaders.CONTENT_TYPE).toLowerCase().contains("json");
        if (paramType.equals(String.class))
            paramData = (json) ? ctx.getBodyAsJson().getValue(paramName) : ctx.getBody
        else if (paramType)
            paramData = ctx.getBody().get*/
        return paramData;
    }

    private Object searchCreatedInstance(Class<?> clazz) {
        for (RouterContext ctx : this.routerList) {
            if (ctx.instanceOf(clazz))
                return ctx.getInstance();
        }
        return null;
    }

    public Router getRouter(){
        return router;
    }
}