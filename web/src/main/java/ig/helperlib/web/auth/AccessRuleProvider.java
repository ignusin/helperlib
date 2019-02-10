package ig.helperlib.web.auth;

import java.util.List;

public interface AccessRuleProvider {
    List<AccessRule> getAccessRules();
}
