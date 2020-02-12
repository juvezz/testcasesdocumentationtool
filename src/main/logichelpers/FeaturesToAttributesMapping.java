package main.logichelpers;

import java.util.HashMap;
import java.util.Map;

public class FeaturesToAttributesMapping {
    public Map<String, String> featuresToAttributeMap = new HashMap<>();
    public FeaturesToAttributesMapping() {
        featuresToAttributeMap.put("add product to Warenkorb", "@addproducttowarenkorb");
        featuresToAttributeMap.put("Checkout as guest", "@checkoutasguest");
        featuresToAttributeMap.put("CP Filters", "@cpfilters");
        featuresToAttributeMap.put("CP layout", "@cplayout");
        featuresToAttributeMap.put("CP preconfiguration", "@cppreconfiguration");
        featuresToAttributeMap.put("CP wishlist", "@cpwishlist");
        featuresToAttributeMap.put("Editing the photofield", "@edditingthephotofield");
        featuresToAttributeMap.put("Editing the text", "@edditingthetext");
        featuresToAttributeMap.put("Edit configurable products", "@editconfigurableproducts");
        featuresToAttributeMap.put("Logging on Jessi page", "@loggingonjessipage");
        featuresToAttributeMap.put("order event service", "@OrderService");
        featuresToAttributeMap.put("PDP configuration", "@pdpconfiguration");
        featuresToAttributeMap.put("PDP layout", "@pdplayout");
        featuresToAttributeMap.put("PDP muster", "@pdpmuster");
        featuresToAttributeMap.put("PDP sharing functionality", "@pdpsharingfunctionality");
        featuresToAttributeMap.put("PDP wishlist", "@pdpwishlist");
        featuresToAttributeMap.put("register to kartenmacherei", "@registertokartenmacherei");
    }
}
