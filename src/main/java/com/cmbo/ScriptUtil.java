package com.cmbo;

/**
 * Created by ognje on 5/27/2017.
 */

//        import ca.sigmasoft.play.omnicare.models.json.exception.OmnicareError;

        import org.apache.bsf.BSFEngine;
        import org.apache.bsf.BSFException;
        import org.apache.bsf.BSFManager;
        import org.apache.commons.lang3.StringUtils;

  //      import play.Logger;

        import java.util.ArrayList;
        import java.util.List;

/**
 *
 * @author This is a utility class to exercise a piece of script
 *         through Apache BSF.
 */
public final class ScriptUtil {
    /** register the BEAN shell engine. */
    private static final String BEANSHELL = "beanshell";

    // The followings are for future enhancement.*/

    /** The name for java shell. */
    // private static final String JAVASHELL = "javashell";

    /** The name for java script. */
    // private static final String JAVASCRIPT = "javascript";

    /*
     * Register supported script types with BSF engine
     */
    static {
        String[] extensions = { "bsh" };
        BSFManager.registerScriptingEngine(
                BEANSHELL, "bsh.util.BeanShellBSFEngine", extensions);
    }

    /** The default result string for success. */
    public static final String RESULT_OK = "";

    /**
     * Execute a piece of Beanshell script.
     *
     * @param script
     *            --> the script need to be executed
     * @param scriptErrors
     *            the script errors
     * @param beans
     *            --> the data object(s) will be passed to the script named as
     *            "parameters"
     * @return the result obtained from the execution of the script
     */
    public static String runBeanShell(final String script, final List<Error> scriptErrors, final Object... beans) {
        return executeScript(BEANSHELL, script, scriptErrors, beans);
    }
    /*public static String runBeanShell(final String script, final List<OmnicareError> scriptErrors, final Object... beans) {
        return executeScript(BEANSHELL, script, scriptErrors, beans);
    }*/

    /**
     * Execute a piece of script using Apache BSF.
     *
     * @param scriptType
     *            --> the type of the script
     * @param script
     *            --> the script need to be executed
     * @param scriptErrors
     *            the script errors
     * @param beans
     *            --> the data object will be passed to the script named as
     *            "parameters"
     * @return the result obtained from the execution of the script
     */
    /*private static String executeScript(final String scriptType, final String script, final List<OmnicareError> scriptErrors,
                                        final Object... beans) {*/
    private static String executeScript(final String scriptType, final String script, final List<Error> scriptErrors,
        final Object... beans) {
        try {
            BSFManager mgr = new BSFManager();
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < beans.length; ++i) {
                list.add(beans[i]);
            }
            mgr.declareBean("parameters", list, List.class);
            BSFEngine engine = mgr.loadScriptingEngine(BEANSHELL);
            String result = (String) engine.eval(BEANSHELL, 0, 0, script);
            if (StringUtils.isEmpty(result)) {
                return RESULT_OK;
            } else {
                /*OmnicareError error = new OmnicareError();
                error.setMessage(result.toString());
                error.setMessageKey("error.validation.script");
                error.addNameValue("script", script);
                scriptErrors.add(error);*/
                return result.toString();
            }

        } catch (BSFException e) {
            //Logger.error("Fail in script execution", e.getMessage(), e);
            return e.getMessage();
        }
    }

    /** Private constructor. */
    private ScriptUtil() {

    }

    /**
     * Execute a piece of script using Apache BSF.
     *
     * @param script
     *            --> the script need to be executed
     * @param beans
     *            --> the data object will be passed to the script named as
     *            "parameters"
     * @return the result obtained from the execution of the script
     */
    public static boolean isValid(final String script, final Object... beans) {
        try {
            //Logger.debug("BeanShell Script:\n {}", script);
            BSFManager mgr = new BSFManager();
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < beans.length; ++i) {
                list.add(beans[i]);
            }
            mgr.declareBean("parameters", list, List.class);
            BSFEngine engine = mgr.loadScriptingEngine(BEANSHELL);
            Object objResult = engine.eval(BEANSHELL, 0, 0, script);
            //boolean result = (boolean) engine.eval(BEANSHELL, 0, 0, script);
            //return result;
            return true;

        } catch (BSFException e) {
            //Logger.error("Fail in script execution:{}", e);
            return true;
        }
    }
}
