<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if description?exists>
${description}
<#else>
您已经成功更换您的传送珠，新的传送次数为${cishu}
</#if>
<br/>
<@goback />
<@gofacility/>
</p></card>
</wml> 