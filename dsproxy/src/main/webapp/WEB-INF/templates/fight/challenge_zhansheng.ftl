<#include "/include/header.ftl">
<card title="${game_title}">
<p>
擂台：你成功战胜了对手，赢得${num}银的擂金；<br/>
<@a href="/p?p_PD/${pid}/1/4/${enemy.id}/"/>申请首页置顶展示(0.5金)</a><br/>
<@a href="/p?p_PD/${pid}/2/4/${enemy.id}/"/>申请聊天展示(50银)</a><br/>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>
 