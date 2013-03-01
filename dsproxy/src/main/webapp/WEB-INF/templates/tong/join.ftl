<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
<#else>
您已申请加入${tong.name}帮，等待帮主${tong.ownerName}批准；请留意您的私聊消息<br/>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>