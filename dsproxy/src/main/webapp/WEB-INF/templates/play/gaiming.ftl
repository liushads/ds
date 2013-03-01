<#include "/include/header.ftl">
<card title="${game_title}"><p>
玄机道长:<br/>
我可以为你逆天改命。只是得收取一些费用<br/>
<@a href="/p?ply_GM/${pid}/gai"/>改命(10银币)</a><br/>
<#if td?exists>
${td}<br/>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>