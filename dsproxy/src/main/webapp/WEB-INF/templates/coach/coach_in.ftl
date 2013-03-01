<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>${desc}<br/></#if>
<@a href="/p?p_LF/${pid}/0/"/>好友</a>|<@a href="/p?p_LI/${pid}/"/>亲友</a>|师徒<br/>
师徒功能请到月牙村点将台了解详情. <br/>
<@goback />
<@gogame />
</p>
</card>
</wml>