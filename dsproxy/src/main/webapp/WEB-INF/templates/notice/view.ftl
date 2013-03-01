<#include "/include/header.ftl">
<card title="${notice.title?if_exists}"><p> 
<#if notice?exists>
${notice.content}<br/>
<#-- ${notice.addTime?string("yyyy-MM-dd")} -->
</#if>
<br/>
<@goback/>
<@qqbar/>
</p>  
</card>  
</wml> 