<#include "/include/header.ftl">

<card title="${game_title}">
<p>
<#if error?exists>
申请失败：${error}
<br/>
<#else>
申请成功<br/>
</#if>

<@gofacility/>
</p></card>
</wml>