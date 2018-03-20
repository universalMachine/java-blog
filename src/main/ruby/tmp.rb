#!/usr/bin/ruby
class Tmp

end
Dir.chdir("/home/extend/IdeaProjects/blog/src/main/java/com/wang/blog")

isDelete = false;
while ARGF.gets
  if $_ =~ /postid/i
    isDelete = true;

  else
    if isDelete == false
      puts $_;
    else

      isDelete = false;
    end
  end
end


=begin
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
=end

