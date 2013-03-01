<#include "/include/header.ftl">
<card title="${game_title}"><p>
元宵节活动<br/>
<#if info?exists>${info}
</#if><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>