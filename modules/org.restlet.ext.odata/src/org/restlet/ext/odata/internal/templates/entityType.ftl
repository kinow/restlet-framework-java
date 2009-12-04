/**
 * Copyright 2005-2009 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package ${packageName};

<#compress>
<#list entityType.importedJavaClasses?sort as clazz>
import ${clazz};
</#list>

<#list entityType.importedEntityTypes?sort as type>
import ${type.fullClassName};
</#list>
</#compress>


<#compress>
/**
 * Generated by the generator tool for the WCF Data Services extension for the Restlet framework.<br>
 * 
<#if metadata.metadataRef??> * @see <a href="${metadata.metadataRef}">Metadata of the target WCF Data Services</a></#if>
 * 
 */
</#compress>

public <#if entityType.abstractType>abstract </#if>class ${className} {

<#list entityType.properties?sort_by("name") as property>
    private ${property.type.javaType} ${property.normalizedName}<#if property.defaultValue??> = property.defaultValue</#if>;
</#list>
<#list entityType.associations?sort_by("name") as association>
    <#if association.toRole.toMany>private List<${association.toRole.type.className}> ${association.toRole.normalizedRole};<#else>private ${association.toRole.type.className} ${association.toRole.normalizedRole};</#if>
</#list>

    /**
     * Constructor without parameter.
     * 
     */
    public ${className}() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param id
     *            The identifiant value of the entity.
     */
    public ${className}(<#if entityType.keys??><#list entityType.keys as key>${key.type.javaType} ${key.normalizedName}<#if key_has_next>, </#if></#list></#if>) {
        this();
<#if entityType.keys??><#list entityType.keys as key>
        this.${key.normalizedName} = ${key.normalizedName};
</#list></#if>
    }
    
<#list entityType.properties?sort_by("name") as property>
   /**
    * Returns the value of the ${property.normalizedName} attribute.
    *
    * @return The value of the ${property.normalizedName} attribute.
    */
   <#if property.getterAccess??>${property.getterAccess}<#else>public</#if> ${property.type.javaType} get${property.normalizedName?cap_first}() {
      return ${property.normalizedName};
   }
   
</#list>
<#list entityType.associations?sort_by("name") as association>
   /**
    * Returns the value of the ${association.toRole.normalizedRole} attribute.
    *
    * @return The value of the ${association.toRole.normalizedRole} attribute.
    */
    <#if association.toRole.toMany>
   public List<${association.toRole.type.className}> get${association.toRole.normalizedRole?cap_first}() {
    <#else>
   public ${association.toRole.type.className} get${association.toRole.normalizedRole?cap_first}() {
    </#if>
      return ${association.toRole.normalizedRole};
   }
   
</#list>
<#list entityType.properties?sort_by("name") as property>
   /**
    * Sets the value of the ${property.normalizedName} attribute.
    *
    * @param ${property.name}
    *     The value of the ${property.normalizedName} attribute.
    */
   <#if property.setterAccess??>${property.setterAccess}<#else>public</#if> void set${property.normalizedName?cap_first}(${property.type.javaType} ${property.normalizedName}) {
      this.${property.normalizedName} = ${property.normalizedName};
   }
   
</#list>
<#list entityType.associations?sort_by("name") as association>
   /**
    * Sets the value of the ${association.toRole.normalizedRole} attribute.
    *
    * @param ${association.toRole.normalizedRole}
    *     The value of the ${association.toRole.normalizedRole} attribute.
    */
    <#if association.toRole.toMany>
   public void set${association.toRole.role?cap_first}(List<${association.toRole.type.className}> ${association.toRole.normalizedRole}) {
    <#else>
   public void set${association.toRole.role?cap_first}(${association.toRole.type.className} ${association.toRole.normalizedRole}) {
    </#if>
      this.${association.toRole.normalizedRole} = ${association.toRole.normalizedRole};
   }

</#list>
}