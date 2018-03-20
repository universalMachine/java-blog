#!/usr/bin/ruby
class Tmp

end
Dir.chdir("/home/extend/IdeaProjects/blog/src/main/java/com/wang/blog")


isPrevEmpty = false
while ARGF.gets
     if $_ =~ /^\s*$/
       if isPrevEmpty
         isPrevEmpty = true
         next
       end

       isPrevEmpty = true
     else
       isPrevEmpty = false
     end
     puts $_

end


