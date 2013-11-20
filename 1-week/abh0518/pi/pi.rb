#!/usr/bin/bash ruby


class MemorizePi

  attr_accessor :level, :numbers, :cache

  def initialize numbers
    @numbers = numbers
    @cache = {}
  end

  def start
    find(0,numbers.length-1)
  end

  def find inx, inj

    key = "#{inx},#{inj}"
    return @cache[key] if !@cache[key].nil?

    remains = inj - inx + 1
    score3, score4, score5 = 10,10,10
    if remains > 2
      score3 = score?(numbers[inx..inx+2]) + find(inx+3, inj) if remains > 2
      score4 = score?(numbers[inx..inx+3]) + find(inx+4, inj) if remains > 3
      score5 = score?(numbers[inx..inx+4]) + find(inx+5, inj) if remains > 4
    else
      if remains < 1
        return 0
      else
        return 10
      end
    end
    result = [score3, score4, score5].min
    @cache[key] = result
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

  #예제 입력
  #5
  p1 = MemorizePi.new [1,2,3,4,1,2,3,4]
  p2 = MemorizePi.new [1,1,1,1,1,2,2,2]
  p3 = MemorizePi.new [1,2,1,2,2,2,2,2]
  p4 = MemorizePi.new [2,2,2,2,2,2,2,2]
  p5 = MemorizePi.new [1,2,6,7,3,9,3,9]

#예제 출력
#  4
#  2
#  5
#  2
#  14

  #puts p1.start
  #puts p2.start
  #puts p3.start
  #puts p4.start
  #puts p5.start


  numbers = []
  10000.times() do
    numbers << Random.rand(9)+1
  end


  pi = MemorizePi.new numbers

  start_time = Time.new
  puts pi.start
  end_time = Time.new
  puts end_time - start_time

end


