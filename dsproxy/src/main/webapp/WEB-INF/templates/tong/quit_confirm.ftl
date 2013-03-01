<#include "/include/header.ftl">
<card title="${game_title}">
<p>
退出帮会会清除您的帮会贡献度，你是否确认退帮？<br/>
<#if tong_player.level==1>
<#if tong.nextOwnerId==0 && (tong.amount>1)>
你是帮主，帮会还没有指定下一任帮主，指定下一任帮主后才能退帮，现在就去<@a href="/p?t_MP/${pid}/"/>指定</a>下一任帮主。<br/>
<#else>
<@a href="/p?t_Q/${pid}/confirm"/>确认退帮</a><br/>
</#if>
</#if>
<#if tong_player.level!=1>
<@a href="/p?t_Q/${pid}/confirm"/>确认退帮</a><br/>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>