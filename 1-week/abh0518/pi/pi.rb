#!/usr/bin/bash ruby

class MemorizePi

  attr_accessor :level, :numbers

  def initialize numbers
    @numbers = numbers
    @level = 0
    @index = 0
  end

  def get_char count

  end


  def score? numbers
    if self.repeat? numbers
      return 1
    elsif self.monotone? numbers
      return 2
    elsif self.rotation? numbers
      return 4
    elsif self.progression? numbers
      return 5
    else
      return 10
    end
  end

  # 하나의 숫자가 반복되는 경우 찾기
  def repeat? numbers
    base = numbers[0]
    numbers.each do |value|
      if value != base then
        return false
      end
    end
    return true
  end

  # 1씩 단조 증가/감소 경우 찾기
  def monotone? numbers
    n = numbers[1] - numbers[0]
    return false if n != 1 && n != -1
    n_before = numbers[0] - n
    numbers.each do |value|
      number = value
      if number != n_before + n then
        return false
      end
      n_before = number
    end
    return true
  end

  # 2개의 숫자가 번갈아가며 나타나는 경우 찾기
  def rotation? numbers
    n_first = numbers[0]
    n_second = numbers[1]
    index = 0
    numbers.each do |value|
      r_section = index%2
      if r_section == 0 && value != n_first
        return false
      elsif r_section == 1 && value != n_second then
        return false
      end
      index += 1
    end
    return true
  end

  # 등차수열인 경우 찾기
  def progression? numbers
    n = numbers[1] - numbers[0]
    return false if n == 0
    n_before = numbers[0] - n
    numbers.each do |value|
      number = value
      if number != n_before + n then
        return false
      end
      n_before = number
    end
    return true
  end

end


if __FILE__ == $0 then
   numbers = [1,2,1,2,1,2,1]
   pi = MemorizePi.new numbers
   puts pi.repeat? numbers
   puts pi.monotone? numbers
   puts pi.rotation? numbers
   puts pi.progression? numbers


end



