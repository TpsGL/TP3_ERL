package visitors.spoon;

import spoon.reflect.CtModel;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class MethodInvocationsCollector {

    private static MethodInvocationsCollector instance;

    private MethodInvocationsCollector() { }

    public static MethodInvocationsCollector getInstance() {
        if (instance == null) {
            instance = new MethodInvocationsCollector();
        }
        return instance;
    }

    public List<CtInvocation> getMethodsInvocation(CtMethod ctMethod) {
        return ctMethod.filterChildren( (CtInvocation i) -> true ).list();
    }




}
